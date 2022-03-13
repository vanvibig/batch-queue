package com.kv.batchqueue.batch;

import com.kv.batchqueue.batch.dto.ActionDto;
import com.kv.batchqueue.batch.dto.KeyDto;
import com.kv.batchqueue.batch.dto.PersonDto;
import com.kv.batchqueue.batch.item.CustomProcessor;
import com.kv.batchqueue.batch.item.CustomReader;
import com.kv.batchqueue.batch.item.CustomWriter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.support.SingleItemPeekableItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StepConfig {

    private final StepBuilderFactory stepBuilderFactory;
    private final BaseConfig baseConfig;

    public StepConfig(StepBuilderFactory stepBuilderFactory, BaseConfig baseConfig) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.baseConfig = baseConfig;
    }

    @Bean
    public Step mergeCsvIdStep(CustomReader<Long> customReader) {
        return stepBuilderFactory.get("mergeCsvIdStep").<KeyDto<Long>, KeyDto<Long>>chunk(10)
                .reader(customReader)
                .processor(new CustomProcessor<>())
                .writer(new CustomWriter<>()).build();
    }

    @Bean
    @StepScope
    public SingleItemPeekableItemReader<PersonDto> masterReader() {
        return baseConfig.createPeekReader("personReader", "batch/person-data.csv", new String[]{"id", "name", "address"}, PersonDto.class);
    }

    @Bean
    @StepScope
    public SingleItemPeekableItemReader<ActionDto> slaveReader() {
        return baseConfig.createPeekReader("actionReader", "batch/action-data.csv", new String[]{"id", "action"}, ActionDto.class);
    }

    @Bean
    @StepScope
    public CustomReader<Long> customReader(SingleItemPeekableItemReader<PersonDto> masterReader, SingleItemPeekableItemReader<ActionDto> slaveReader) {
        var reader = new CustomReader<Long>();
        reader.setMasterReader(masterReader);
        reader.addDelegates(slaveReader);
        return reader;
    }
}
