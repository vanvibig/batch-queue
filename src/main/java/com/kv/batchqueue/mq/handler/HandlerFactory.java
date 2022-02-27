package com.kv.batchqueue.mq.handler;

import com.kv.batchqueue.entity.CustomerEntity;
import lombok.experimental.UtilityClass;
import org.springframework.messaging.Message;

import java.util.Map;
import java.util.Objects;

@UtilityClass
public class HandlerFactory {
    private static final Map<String, Object> mapHandler = Map.of(
            String.class.getName(), new StringMqHandler(),
            CustomerEntity.class.getName(), new EntityMqHandler()
    );

    @SuppressWarnings("unchecked")
    public static <T> MqHandler<T> getHandler(Message<T> message) {
        var type = Objects.requireNonNull(message.getHeaders().get("_type")).toString();
        return (MqHandler<T>) mapHandler.get(type);
    }
}
