package com.hmily.disruptor.heigh;

import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Handler4 implements EventHandler<Trade> {

    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        log.info("handler 4 : SET PRICE");
        Thread.sleep(1000);
        event.setPrice(17.0);
    }
}
