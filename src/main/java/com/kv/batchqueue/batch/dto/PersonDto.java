package com.kv.batchqueue.batch.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PersonDto implements KeyDto<Long> {
    private Long id;
    private String name;
    private String address;
    private Action action;

    @Override
    public Long getKey() {
        return id;
    }
}
