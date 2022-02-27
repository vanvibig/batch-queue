package com.kv.batchqueue.mq;

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

    @Value("${custom.mq.destination}")
    private String mqDestination;

    public MqService(JmsTemplate jmsTemplate, JmsListenerEndpointRegistry registry) {
        this.jmsTemplate = jmsTemplate;
        this.registry = registry;
    }

    private String getJmsListenerId() {
        return mqDestination + "-listener";
    }

    @SneakyThrows
    public void sendMessage() {
        var msg1 = DataUtil.nextCustomer();
        jmsTemplate.convertAndSend(mqDestination, msg1);
        String msg2 = "test any string";
        jmsTemplate.convertAndSend(mqDestination, msg2);
        log.info("send message: {}", msg1);
        log.info("send message: {}", msg2);
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
