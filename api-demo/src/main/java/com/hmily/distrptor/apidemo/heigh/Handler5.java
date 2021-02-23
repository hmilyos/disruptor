package com.hmily.distrptor.apidemo.heigh;

import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Handler5 implements EventHandler<Trade> {

    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        log.info("handler 5 : get PRICE: {}", event.getPrice());
        Thread.sleep(1000);
        event.setPrice(event.getPrice() + 3);
    }
}
