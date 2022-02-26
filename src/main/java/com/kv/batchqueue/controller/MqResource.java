package com.kv.batchqueue.controller;

import com.kv.batchqueue.mq.MqService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqResource {

    private final MqService mqService;

    public MqResource(MqService mqService) {
        this.mqService = mqService;
    }

    @GetMapping("send")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void send() {
        mqService.sendMessage();
    }

    @GetMapping("receive")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void receive() {
        mqService.receiveMessage();
    }

    @GetMapping("start-jms-listener")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void startJmsListener() {
        mqService.startJmsListener();
    }

    @GetMapping("stop-jms-listener")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void stopJmsListener() {
        mqService.stopJmsListener();
    }
}
