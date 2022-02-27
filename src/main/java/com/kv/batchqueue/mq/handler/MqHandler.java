package com.kv.batchqueue.mq.handler;

public interface MqHandler<T> {
    void handle(T message);
}
