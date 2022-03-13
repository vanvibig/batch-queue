package com.kv.batchqueue.batch.item;

import com.kv.batchqueue.batch.dto.KeyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@StepScope
@Slf4j
public class CustomWriter implements ItemWriter<KeyDto<Long>> {

    @Override
    public void write(List<? extends KeyDto<Long>> items) {
        log.info(String.valueOf(items));
    }
}
