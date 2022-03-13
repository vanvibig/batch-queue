package com.kv.batchqueue.batch.item;

import com.kv.batchqueue.batch.dto.KeyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.PassThroughItemProcessor;

@Slf4j
public class CustomProcessor<K extends Comparable<K>> extends PassThroughItemProcessor<KeyDto<K>> {

    @Override
    public KeyDto<K> process(KeyDto<K> item) {
        log.info(String.valueOf(item));
        return item;
    }
}
