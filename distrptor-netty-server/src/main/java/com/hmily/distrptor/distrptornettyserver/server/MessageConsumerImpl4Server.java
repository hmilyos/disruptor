package com.hmily.distrptor.distrptornettyserver.server;


import com.hmily.distrptor.distrptornettycommon.disruptor.MessageConsumer;
import com.hmily.distrptor.distrptornettycommon.dto.TranslatorData;
import com.hmily.distrptor.distrptornettycommon.dto.TranslatorDataWapper;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageConsumerImpl4Server extends MessageConsumer {

    public MessageConsumerImpl4Server(String consumerId) {
        super(consumerId);
    }

    @Override
    public void onEvent(TranslatorDataWapper event) throws Exception {
        TranslatorData request = event.getData();
        ChannelHandlerContext ctx = event.getCtx();
        //1.业务处理逻辑:
        log.info("server端: id: {}, name: {}, message: {}", request.getId(), request.getName(), request.getMessage());
        //2.回送响应信息:
        TranslatorData resp = TranslatorData.builder()
                .id("resp: " + request.getId())
                .name("resp: " + request.getName())
                .message("resp: " + request.getMessage())
                .build();

        //写出response响应信息:
        ctx.writeAndFlush(resp);
    }
}
