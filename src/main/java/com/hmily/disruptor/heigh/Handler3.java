package com.hmily.disruptor.heigh;


import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Handler3 implements EventHandler<Trade> {
    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        log.info("handler 3, name: {}, id: {}, price: {}, instance: {}", event.getName(), event.getId(), event.getPrice(), event);
    }
}
