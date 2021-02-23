package com.hmily.distrptor.distrptornettycommon.disruptor;


import com.hmily.distrptor.distrptornettycommon.dto.TranslatorDataWapper;
import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.util.concurrent.*;

public class RingBufferWorkerPoolFactory {

    private Logger log = LoggerFactory.getLogger(RingBufferWorkerPoolFactory.class);


    private static class SingletonHolder {
        static final RingBufferWorkerPoolFactory instance = new RingBufferWorkerPoolFactory();
    }

    private RingBufferWorkerPoolFactory() {
    }

    public static RingBufferWorkerPoolFactory getInstance() {
        return SingletonHolder.instance;
    }

    private static Map<String, MessageProducer> producers = new ConcurrentHashMap<String, MessageProducer>();

    private static Map<String, MessageConsumer> consumers = new ConcurrentHashMap<String, MessageConsumer>();

    private RingBuffer<TranslatorDataWapper> ringBuffer;

    private SequenceBarrier sequenceBarrier;

    private WorkerPool<TranslatorDataWapper> workerPool;


    public void initAndStart(ProducerType type, int bufferSize, WaitStrategy waitStrategy, MessageConsumer[] messageConsumers) {
        //1. 构建ringBuffer对象
        this.ringBuffer = RingBuffer.create(type, TranslatorDataWapper::new, bufferSize, waitStrategy);

        //2.设置序号栅栏
        this.sequenceBarrier = this.ringBuffer.newBarrier();

        //3.设置工作池
        this.workerPool = new WorkerPool<>(this.ringBuffer, this.sequenceBarrier, new EventExceptionHandler(), messageConsumers);

        //4 把所构建的消费者置入池中
        for (MessageConsumer consumer : messageConsumers) {
            consumers.put(consumer.getConsumerId(), consumer);
        }

        //5 添加我们的sequences
        this.ringBuffer.addGatingSequences(this.workerPool.getWorkerSequences());

        //6 启动我们的工作池
//        this.workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2));
        ThreadPoolExecutor pool = new ThreadPoolExecutor(3,
                Runtime.getRuntime().availableProcessors() / 2,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1024 * 1024),
                r -> {
                    Thread t = new Thread(r);
                    t.setName("DistrptorThread");
                    if (t.isDaemon()) {
                        t.setDaemon(false);
                    }
                    if (Thread.NORM_PRIORITY != t.getPriority()) {
                        t.setPriority(Thread.NORM_PRIORITY);
                    }
                    return t;
                },
                (r, executor) -> {
                    log.error("RingBufferWorkerPoolFactory Queue 不够用了");
                    log.error("RingBufferWorkerPoolFactory Queue 拒绝策略: {}", r);
                });
        this.workerPool.start(pool);

    }

    public MessageProducer getMessageProducer(String producerId) {
        MessageProducer producer = producers.get(producerId);
        if (producer == null) {
            producer = new MessageProducer(producerId, this.ringBuffer);
            producers.put(producerId, producer);
        }
        return producer;
    }


    /**
     * 异常静态类
     *
     * @author Alienware
     */
    static class EventExceptionHandler implements ExceptionHandler<TranslatorDataWapper> {
        public void handleEventException(Throwable ex, long sequence, TranslatorDataWapper event) {
        }

        public void handleOnStartException(Throwable ex) {
        }

        public void handleOnShutdownException(Throwable ex) {
        }
    }
}
