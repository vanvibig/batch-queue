package com.kv.batchqueue.batch.item;

import com.kv.batchqueue.batch.dto.KeyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
public class CustomWriter<K extends Comparable<K>> implements ItemWriter<KeyDto<K>> {

    @Override
    public void write(List<? extends KeyDto<K>> items) {
        log.info(String.valueOf(items));
    }
}
