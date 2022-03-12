package com.kv.batchqueue.batch.dto;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class MapperUtil {

    public static <T> boolean update(T dest, T sour) {
        if (dest instanceof PersonDto && sour instanceof ActionDto) {
            log.info("Merge {} into {}", sour, dest);
            ((PersonDto) dest).setAction(((ActionDto) sour).getAction());
            return true;
        }
        return false;
    }
}
