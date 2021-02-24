package com.hmily.distrptor.distrptornettyclient.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class Test {

    @Autowired
    private NettyClient nettyClient;

    @PostConstruct
    public void init() {
        nettyClient.sendData();
    }
}
