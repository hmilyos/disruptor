package com.hmily.disruptor.heigh.multi;


import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Consumer implements WorkHandler<Order> {

    private String comsumerId;

    private static AtomicInteger count = new AtomicInteger(0);

    private Random random = new Random();

    public Consumer(String comsumerId) {
        this.comsumerId = comsumerId;
    }

    @Override
    public void onEvent(Order event) throws Exception {
        Thread.sleep(random.nextInt(5));
        log.info("当前消费者: {}, 消费信息ID: {}", this.comsumerId, event.getId());
        count.incrementAndGet();
    }

    public int getCount(){
        return count.get();
    }
}
