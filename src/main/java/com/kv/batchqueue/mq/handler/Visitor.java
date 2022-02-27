package com.kv.batchqueue.mq.handler;

import com.kv.batchqueue.entity.CustomerEntity;

public interface Visitor {
    void visit(String payload);
    void visit(CustomerEntity payload);
    <T> void visit(T payload);
}
