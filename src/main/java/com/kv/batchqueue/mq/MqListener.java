package com.kv.batchqueue.mq;

import com.kv.batchqueue.mq.handler.Visitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;

@Slf4j
@Configuration
public class MqListener {

    private final Visitor visitor;

    public MqListener(Visitor visitor) {
        this.visitor = visitor;
    }

    @JmsListener(id = "${custom.mq.destination}-listener", destination = "${custom.mq.destination}", containerFactory = "jmsListenerContainerFactory")
    public <T> void onMessage(Message<T> message) {
        var payload = message.getPayload();
        visitor.visit(payload);

        //todo limit retry and return message to queue if max retry
        //todo handle differ type of messages: String, CustomerEntity, ...
        //todo insert message to record in db
        //todo spring batch read record from db to write a file
        //todo spring batch merge file
        //todo insert merge file
    }
}
