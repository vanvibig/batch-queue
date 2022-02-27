package com.kv.batchqueue.mq.handler;

import com.kv.batchqueue.entity.CustomerEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageVisitor implements Visitor {
    @Override
    public void visit(String payload) {
        log.info("visit string payload: {}", payload);
    }

    @Override
    public void visit(CustomerEntity payload) {
        log.info("visit entity payload: {}", payload);
    }

    @Override
    public <T> void visit(T payload) {
        var type = payload.getClass().getSimpleName();
        switch (type) {
            case "String":
                visit((String) payload);
                break;
            case "CustomerEntity":
                visit((CustomerEntity) payload);
                break;
            default:
                throw new RuntimeException("Unsupported payload type: " + payload);
        }
    }
}
