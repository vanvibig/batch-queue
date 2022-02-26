package com.kv.batchqueue.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PingResource {

    @GetMapping(value = "ping")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void ping() {
        log.info("pong");
    }
}
