package com.kv.batchqueue.batch.item;

import com.kv.batchqueue.batch.dto.KeyDto;
import com.kv.batchqueue.batch.dto.MapperUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamSupport;
import org.springframework.batch.item.file.FlatFileItemReader;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CustomReader<K extends Comparable<K>> extends ItemStreamSupport implements ItemReader<KeyDto<K>> {

    @Setter
    private FlatFileItemReader<? extends KeyDto<K>> masterReader;

    private final List<FlatFileItemReader<? extends KeyDto<K>>> delegates = new ArrayList<>();

    @Override
    public KeyDto<K> read() throws Exception {
        var masterDto = masterReader.read();

        if (masterDto == null) {
            return null;
        }

        for (var delegate : delegates) {
            while (true) {
                var slaveDto = delegate.read();
                if (slaveDto == null) {
                    break;
                }

                if (MapperUtil.update(masterDto, slaveDto)) {
                    break;
                }
            }
        }

        return masterDto;
    }

    public void addDelegates(FlatFileItemReader<? extends KeyDto<K>> reader) {
        this.delegates.add(reader);
    }

    @Override
    public void close() {
        super.close();
        masterReader.close();
        delegates.forEach(ItemStream::close);
    }

    @Override
    public void open(ExecutionContext executionContext) {
        super.open(executionContext);
        masterReader.open(executionContext);
        delegates.forEach(delegate -> delegate.open(executionContext));
    }

    @Override
    public void update(ExecutionContext executionContext) {
        super.update(executionContext);
        masterReader.update(executionContext);
        delegates.forEach(delegate -> delegate.update(executionContext));
    }
}