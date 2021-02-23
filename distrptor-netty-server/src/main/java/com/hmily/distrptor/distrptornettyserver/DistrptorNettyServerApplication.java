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

        MessageConsumer[] conusmers = new MessageConsumer[4];
        for (int i = 0; i < conusmers.length; i++) {
            MessageConsumer messageConsumer = new MessageConsumerImpl4Server("code:serverConsumerId:" + i);
            conusmers[i] = messageConsumer;
        }
        RingBufferWorkerPoolFactory.getInstance().initAndStart(ProducerType.MULTI,
                1024 * 1024,
                //new YieldingWaitStrategy(),
                new BlockingWaitStrategy(),
                conusmers);

        new NettyServer();
    }

}
