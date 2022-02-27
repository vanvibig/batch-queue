package com.kv.batchqueue.mq;

import com.kv.batchqueue.mq.handler.HandlerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;

@Slf4j
@Configuration
public class MqListener {

    @JmsListener(id = "${custom.mq.destination}-listener", destination = "${custom.mq.destination}", containerFactory = "jmsListenerContainerFactory")
    public <T> void onMessage(Message<T> message) {
        //log.info("onMessage: {}", message.getPayload());

        var payload = message.getPayload();

        var handler = HandlerFactory.getHandler(message);
        handler.handle(payload);

        log.info("onMessage: {}", message.getPayload());
        //todo limit retry and return message to queue if max retry
        //todo handle differ type of messages: String, CustomerEntity, ...
        //todo insert message to record in db
        //todo spring batch read record from db to write a file
        //todo spring batch merge file
        //todo insert merge file
    }
}
