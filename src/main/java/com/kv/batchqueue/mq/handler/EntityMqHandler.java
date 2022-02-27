package com.kv.batchqueue.mq.handler;

import com.kv.batchqueue.entity.CustomerEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EntityMqHandler implements MqHandler<CustomerEntity> {
    @Override
    public void handle(CustomerEntity message) {
        log.info("handle entity message: {}", message);
    }
}

