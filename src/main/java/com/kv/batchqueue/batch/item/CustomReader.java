package com.kv.batchqueue.batch.item;

import com.kv.batchqueue.batch.dto.KeyDto;
import com.kv.batchqueue.batch.dto.MapperUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamSupport;
import org.springframework.batch.item.support.SingleItemPeekableItemReader;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CustomReader<K extends Comparable<K>> extends ItemStreamSupport implements ItemReader<KeyDto<K>> {

    @Setter
    private SingleItemPeekableItemReader<? extends KeyDto<K>> masterReader;

    private final List<SingleItemPeekableItemReader<? extends KeyDto<K>>> delegates = new ArrayList<>();

    @Override
    public KeyDto<K> read() throws Exception {
        var masterDto = masterReader.read();

        if (masterDto == null) {
            return null;
        }

        for (var delegate : delegates) {
            while (true) {
                var slaveDto = delegate.peek();
                if (slaveDto == null) {
                    break;
                }

                var compareResult = MapperUtil.compare(masterDto, slaveDto);

                if (compareResult == 0) {
                    MapperUtil.update(masterDto, slaveDto);
                    delegate.read();
                    break;
                }

                if (MapperUtil.compare(masterDto, slaveDto) == -1) {
                    break;
                }

                if (MapperUtil.compare(masterDto, slaveDto) == 1) {
                    delegate.read();
                }
            }
        }

        return masterDto;
    }

    public void addDelegates(SingleItemPeekableItemReader<? extends KeyDto<K>> reader) {
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
