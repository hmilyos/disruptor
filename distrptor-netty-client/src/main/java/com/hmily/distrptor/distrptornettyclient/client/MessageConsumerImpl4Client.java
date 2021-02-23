package com.hmily.distrptor.distrptornettyclient.client;


import com.hmily.distrptor.distrptornettycommon.disruptor.MessageConsumer;
import com.hmily.distrptor.distrptornettycommon.dto.TranslatorData;
import com.hmily.distrptor.distrptornettycommon.dto.TranslatorDataWapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageConsumerImpl4Client extends MessageConsumer {
    public MessageConsumerImpl4Client(String consumerId) {
        super(consumerId);
    }

    @Override
    public void onEvent(TranslatorDataWapper event) throws Exception {
        TranslatorData response = event.getData();
        ChannelHandlerContext ctx = event.getCtx();
        //业务逻辑处理:
        try {
            log.info("Client端: id: {}, name: {}, message: {}", response.getId(), response.getName(), response.getMessage());
        } finally {
            ReferenceCountUtil.release(response);
        }
    }
}
