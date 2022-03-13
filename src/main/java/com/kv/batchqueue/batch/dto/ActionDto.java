package com.kv.batchqueue.batch.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ActionDto implements KeyDto<Long> {
    private Long id;
    private Action action;

    @Override
    public Long getKey() {
        return id;
    }
}
