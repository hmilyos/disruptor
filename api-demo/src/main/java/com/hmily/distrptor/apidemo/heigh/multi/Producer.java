package com.hmily.distrptor.apidemo.heigh.multi;

import com.lmax.disruptor.RingBuffer;


public class Producer {

    private RingBuffer<Order> ringBuffer;

    public Producer(RingBuffer<Order> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void sendData(String uuid) {
        long sequence = ringBuffer.next();
        Order order = ringBuffer.get(sequence);
        order.setId(uuid);
        ringBuffer.publish(sequence);
    }
}
