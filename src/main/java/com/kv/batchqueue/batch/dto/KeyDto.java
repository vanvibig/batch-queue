package com.kv.batchqueue.batch.dto;

public interface KeyDto<K extends Comparable<K>> {
    K getKey();
}
