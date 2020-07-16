package com.moke.rocketmq;

import java.io.Serializable;

/**
 * @program: myProject
 * @ClassName OrderPaidEvent
 * @description: [用一句话描述此类]
 * @author: GUO
 * @create: 2020-06-10 21:44
 **/

public class OrderPaidEvent implements Serializable {

    String orderId;

    public OrderPaidEvent() {
    }

    @Override
    public String toString() {
        return "OrderPaidEvent{" +
                "orderId='" + orderId + '\'' +
                '}';
    }

    public OrderPaidEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Object build() {
        return null;
    }
}
