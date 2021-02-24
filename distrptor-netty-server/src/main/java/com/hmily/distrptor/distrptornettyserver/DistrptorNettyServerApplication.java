package com.hmily.distrptor.distrptornettyserver;

import com.hmily.distrptor.distrptornettycommon.disruptor.MessageConsumer;
import com.hmily.distrptor.distrptornettycommon.disruptor.RingBufferWorkerPoolFactory;
import com.hmily.distrptor.distrptornettyserver.server.MessageConsumerImpl4Server;
import com.hmily.distrptor.distrptornettyserver.server.NettyServer;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DistrptorNettyServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistrptorNettyServerApplication.class, args);

//        new NettyServer();
    }

}
