package com.kv.batchqueue.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MqService {
    private final JmsTemplate jmsTemplate;
    private final JmsListenerEndpointRegistry registry;

    @Value("${custom.mq.destination}")
    private String mqDestination;

    public MqService(JmsTemplate jmsTemplate, JmsListenerEndpointRegistry registry) {
        this.jmsTemplate = jmsTemplate;
        this.registry = registry;
    }

    private String getJmsListenerId() {
        return mqDestination + "-listener";
    }

    public void sendMessage() {
        var msg = "Hello World!";
        jmsTemplate.convertAndSend(mqDestination, msg);
        log.info("send message: {}", msg);
    }

    public void startJmsListener() {
        var jmsListenerId = getJmsListenerId();
        registry.getListenerContainer(jmsListenerId);
        registry.start();
        log.info("started {}", jmsListenerId);
    }

    public void stopJmsListener() {
        var jmsListenerId = getJmsListenerId();
        registry.getListenerContainer(jmsListenerId);
        registry.stop();
        log.info("stopped {}", jmsListenerId);
    }
}
