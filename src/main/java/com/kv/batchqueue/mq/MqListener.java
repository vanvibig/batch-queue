package com.kv.batchqueue.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;

@Slf4j
@Configuration
public class MqListener {

    @JmsListener(id = "${custom.mq.destination}-listener", destination = "${custom.mq.destination}", containerFactory = "jmsListenerContainerFactory")
    public void onObjectMessage(Message<?> message) {
        log.info("onMessage: {}", message.getPayload());
        //todo limit retry and return message to queue if max retry
    }
}
