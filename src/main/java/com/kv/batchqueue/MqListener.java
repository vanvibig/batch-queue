package com.kv.batchqueue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;

@Slf4j
@Configuration
public class MqListener {

    @JmsListener(id = "${custom.mq.destination}-listener", destination = "${custom.mq.destination}", containerFactory = "jmsListenerContainerFactory")
    public void onMessage(Message<String> message) {
        log.info("onMessage: {}", message.getPayload());
    }

}
