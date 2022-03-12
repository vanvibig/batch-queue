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
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class StepConfig {

    public final StepBuilderFactory stepBuilderFactory;

    public StepConfig(StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Step mergeCsvIdStep(CustomReader<String> customReader, CustomProcessor processor, CustomWriter writer) {
        return stepBuilderFactory.get("mergeCsvIdStep").<KeyDto<String>, KeyDto<String>>chunk(10).reader(customReader).processor(processor).writer(writer).build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<PersonDto> masterReader() {
        return new FlatFileItemReaderBuilder<PersonDto>().name("personReader").resource(new ClassPathResource("batch/person-data.csv")).delimited().names(new String[]{"id", "name", "address"}).fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
            setTargetType(PersonDto.class);
        }}).build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<ActionDto> slaveReader() {
        return new FlatFileItemReaderBuilder<ActionDto>().name("slaveReader").resource(new ClassPathResource("batch/action-data.csv")).delimited().names(new String[]{"id", "action", "personId"}).fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
            setTargetType(ActionDto.class);
        }}).build();
    }

    @Bean
    @StepScope
    public CustomReader<String> customReader(FlatFileItemReader<PersonDto> masterReader, FlatFileItemReader<ActionDto> slaveReader) {
        var reader = new CustomReader<String>();
        reader.setMasterReader(masterReader);
        reader.addDelegates(slaveReader);
        return reader;
    }

}
