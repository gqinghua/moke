package com.moke.vas.main.redission;

import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * Created by dongdongqin on 2018-10-09.
 */
public class RedisPutInQueue {

    public static void main(String[] args) throws Exception {

        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.19.173:6379").setPassword("ps666").setDatabase(2);
        RedissonClient redissonClient = Redisson.create(config);
        RBlockingQueue<CallCdr> blockingFairQueue = redissonClient.getBlockingQueue("delay_queue");
        RDelayedQueue<CallCdr> delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);

        // register for every transaction

        CallCdr callCdr = new CallCdr();
        delayedQueue.offer(callCdr, 1, TimeUnit.MINUTES);

        // take the key if time is coming, the operation is same
        blockingFairQueue.take();



    }
}
