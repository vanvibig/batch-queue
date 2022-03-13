package com.kv.batchqueue.batch;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.support.SingleItemPeekableItemReader;
import org.springframework.batch.item.support.builder.SingleItemPeekableItemReaderBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class BaseConfig {

    public <T> FieldSetMapper<T> createMapper(Class<T> clazz) {
        return new BeanWrapperFieldSetMapper<>() {{
            setTargetType(clazz);
        }};
    }

    public <T> FlatFileItemReader<T> createFileReader(String name, String path, String[] names, Class<T> clazz) {
        return new FlatFileItemReaderBuilder<T>()
                .name(name)
                .resource(new ClassPathResource(path))
                .delimited()
                .names(names)
                .fieldSetMapper(createMapper(clazz))
                .build();
    }

    public <T> SingleItemPeekableItemReader<T> createPeekReader(String name, String path, String[] names, Class<T> clazz) {
        return new SingleItemPeekableItemReaderBuilder<T>()
                .delegate(createFileReader(name, path, names, clazz))
                .build();
    }
}
