package com.kv.batchqueue.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kv.batchqueue.entity.CustomerEntity;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.jeasy.random.EasyRandom;

@UtilityClass
public class DataUtil {
    private static final EasyRandom generator = new EasyRandom();

    @SneakyThrows
    public static String nextCustomer(ObjectMapper objectMapper) {
        return objectMapper.writeValueAsString(generator.nextObject(CustomerEntity.class));
    }

    public static CustomerEntity nextCustomer() {
        return generator.nextObject(CustomerEntity.class);
    }

}
