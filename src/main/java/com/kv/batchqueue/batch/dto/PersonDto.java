package com.kv.batchqueue.batch.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PersonDto implements KeyDto<String> {
    private String id;
    private String name;
    private String address;
    private Action action;

    @Override
    public String getKey() {
        return id;
    }
}
