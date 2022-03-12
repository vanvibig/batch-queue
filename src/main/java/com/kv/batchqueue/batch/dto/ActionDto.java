package com.kv.batchqueue.batch.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ActionDto implements KeyDto<String> {
    private String id;
    private Action action;
    private String personId;

    @Override
    public String getKey() {
        return personId;
    }
}
