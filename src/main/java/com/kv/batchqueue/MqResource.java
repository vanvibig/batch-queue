package com.kv.batchqueue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MqResource {

    private final JmsTemplate jmsTemplate;
    private final JmsListenerEndpointRegistry registry;

    @Value("${custom.mq.destination}")
    private String mqDestination;


    public MqResource(JmsTemplate jmsTemplate, JmsListenerEndpointRegistry registry) {
        this.jmsTemplate = jmsTemplate;
        this.registry = registry;
    }

    private String getJmsListenerId() {
        return mqDestination + "-listener";
    }

    @GetMapping("send")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void send() {
        var msg = "Hello World!";
        jmsTemplate.convertAndSend(mqDestination, msg);
        log.info("send message: {}", msg);
    }

    @GetMapping("start-jms-listener")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void startJmsListener() {
        var jmsListenerId = getJmsListenerId();
        registry.getListenerContainer(jmsListenerId);
        registry.start();
        log.info("started {}", jmsListenerId);
    }

    @GetMapping("stop-jms-listener")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void stopJmsListener() {
        var jmsListenerId = getJmsListenerId();
        registry.getListenerContainer(jmsListenerId);
        registry.stop();
        log.info("stopped {}", jmsListenerId);
    }
}
