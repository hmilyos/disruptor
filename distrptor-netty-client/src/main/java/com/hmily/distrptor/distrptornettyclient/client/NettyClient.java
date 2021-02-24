package com.hmily.distrptor.distrptornettyclient.client;


import com.hmily.distrptor.distrptornettycommon.codec.MarshallingCodeCFactory;
import com.hmily.distrptor.distrptornettycommon.disruptor.MessageConsumer;
import com.hmily.distrptor.distrptornettycommon.disruptor.RingBufferWorkerPoolFactory;
import com.hmily.distrptor.distrptornettycommon.dto.TranslatorData;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class NettyClient {

    public static final String HOST = "127.0.0.1";
    public static final int PORT = 8765;

    //扩展 完善 池化: ConcurrentHashMap<KEY -> String, Value -> Channel>
    private Channel channel;

    //1. 创建工作线程组: 用于实际处理业务的线程组
    private EventLoopGroup workGroup = new NioEventLoopGroup();

    private ChannelFuture cf;

    @PostConstruct
    public void init() {
        MessageConsumer[] conusmers = new MessageConsumer[4];
        for (int i = 0; i < conusmers.length; i++) {
            MessageConsumer messageConsumer = new MessageConsumerImpl4Client("code:clientId:" + i);
            conusmers[i] = messageConsumer;
        }
        RingBufferWorkerPoolFactory.getInstance().initAndStart(ProducerType.MULTI,
                1024 * 1024,
                //new YieldingWaitStrategy(),
                new BlockingWaitStrategy(),
                conusmers);

        this.connect(HOST, PORT);
    }

    private void connect(String host, int port) {
        //2 辅助类(注意Client 和 Server 不一样)
        Bootstrap bootstrap = new Bootstrap();

        try {
            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    //表示缓存区动态调配（自适应）
                    .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                    //缓存区 池化操作
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                            sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                            sc.pipeline().addLast(new ClientHandler());
                        }
                    });
            //绑定端口，同步等等请求连接
            this.cf = bootstrap.connect(host, port).sync();
            log.info("NettyClient connected...");

            //接下来就进行数据的发送, 但是首先我们要获取channel:
            this.channel = cf.channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendData(){

        for(int i =0; i <10; i++){
            TranslatorData request = TranslatorData.builder()
                    .id("" + i).name("请求消息名称 " + i).message("请求消息内容 " + i)
                    .build();
            this.channel.writeAndFlush(request);
        }
    }

    public void close() throws Exception {
        cf.channel().closeFuture().sync();
        //优雅停机
        workGroup.shutdownGracefully();
        log.info("NettyClient ShutDown...");
    }

}
