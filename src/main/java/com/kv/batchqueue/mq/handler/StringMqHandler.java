package com.kv.batchqueue.mq.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StringMqHandler implements MqHandler<String> {
    @Override
    public void handle(String message) {
        log.info("handle string message: {}", message);
    }
}
