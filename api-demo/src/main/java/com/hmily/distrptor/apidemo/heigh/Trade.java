package com.hmily.distrptor.apidemo.heigh;


import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Trade {

    private String id;
    private String name;
    private double price;
    private AtomicInteger count = new AtomicInteger(0);

}
