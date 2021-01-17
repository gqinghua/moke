package com.moke.vas.main.backpressure;


import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.CountDownLatch;

/**
 * Created by dongdongqin on 2018-10-25.
 */
public class BackPressureDemo {


    public static void main(String[] args) {


        CountDownLatch countDownLatch = new CountDownLatch(1);

        Flowable
                .create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                        int i = 0;
                        while (true) {
                            if (e.requested() == 0) continue;//此处添加代码，让flowable按需发送数据
                            System.out.println("发射---->" + i);
                            i++;
                            e.onNext(i);
                        }
                    }
                }, BackpressureStrategy.MISSING)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {
                    private Subscription mSubscription;

                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(1);            //设置初始请求数据量为1
                        mSubscription = s;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        try {
                            Thread.sleep(500);
                            System.out.println("接收------>" + integer);
                            mSubscription.request(1);//每接收到一条数据增加一条请求量
                        } catch (InterruptedException ignore) {
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });

        try {
            countDownLatch.await();
        } catch (Exception ex) {

        }

    }

}
