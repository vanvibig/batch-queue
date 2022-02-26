package com.kv.batchqueue.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kv.batchqueue.util.DataUtil;
import lombok.SneakyThrows;
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
    private final ObjectMapper objectMapper;

    @Value("${custom.mq.destination}")
    private String mqDestination;

    public MqService(JmsTemplate jmsTemplate, JmsListenerEndpointRegistry registry, ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.registry = registry;
        this.objectMapper = objectMapper;
    }

    private String getJmsListenerId() {
        return mqDestination + "-listener";
    }

    @SneakyThrows
    public void sendMessage() {
        var msg = DataUtil.nextCustomer();
        jmsTemplate.convertAndSend(mqDestination, msg);
        jmsTemplate.convertAndSend(mqDestination, "test any string");
        log.info("send message: {}", msg);
    }

    public void receiveMessage() {
        var msg = jmsTemplate.receiveAndConvert(mqDestination);
        log.info("receive message: {}", msg);
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
