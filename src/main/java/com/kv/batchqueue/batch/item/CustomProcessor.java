package com.kv.batchqueue.batch.item;

import com.kv.batchqueue.batch.dto.KeyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.support.PassThroughItemProcessor;
import org.springframework.stereotype.Component;

@Component
@StepScope
@Slf4j
public class CustomProcessor extends PassThroughItemProcessor<KeyDto<String>> {

    @Override
    public KeyDto<String> process(KeyDto<String> item) {
        log.info(String.valueOf(item));
        return item;
    }
}
