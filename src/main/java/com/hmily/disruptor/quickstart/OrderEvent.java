package com.hmily.disruptor.quickstart;

import lombok.Data;

@Data
public class OrderEvent {

    /**
     * 订单的价格
     */
    private long value;

}
