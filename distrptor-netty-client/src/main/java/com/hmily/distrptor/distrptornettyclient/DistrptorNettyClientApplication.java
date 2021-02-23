package com.hmily.distrptor.distrptornettyclient;

import com.hmily.distrptor.distrptornettyclient.client.MessageConsumerImpl4Client;
import com.hmily.distrptor.distrptornettyclient.client.NettyClient;
import com.hmily.distrptor.distrptornettycommon.disruptor.MessageConsumer;
import com.hmily.distrptor.distrptornettycommon.disruptor.RingBufferWorkerPoolFactory;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DistrptorNettyClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistrptorNettyClientApplication.class, args);

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

        //建立连接 并发送消息
        new NettyClient().sendData();

    }

}
