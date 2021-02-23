package com.hmily.distrptor.distrptornettyclient.client;


import com.hmily.distrptor.distrptornettycommon.disruptor.MessageProducer;
import com.hmily.distrptor.distrptornettycommon.disruptor.RingBufferWorkerPoolFactory;
import com.hmily.distrptor.distrptornettycommon.dto.TranslatorData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        TranslatorData response = (TranslatorData)msg;
        String producerId = "code:seesionId:002";
        MessageProducer messageProducer = RingBufferWorkerPoolFactory.getInstance().getMessageProducer(producerId);
        messageProducer.onData(response, ctx);

    }
}
