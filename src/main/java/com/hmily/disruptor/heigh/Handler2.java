package com.hmily.disruptor.heigh;


import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class Handler2 implements EventHandler<Trade> {

    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        log.info("handler 2 : SET ID");
        Thread.sleep(2000);
        event.setId(UUID.randomUUID().toString());
    }
}
