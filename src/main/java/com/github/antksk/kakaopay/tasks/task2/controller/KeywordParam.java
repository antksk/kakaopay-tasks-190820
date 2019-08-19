package com.github.antksk.kakaopay.tasks.task2.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

@Value
public class KeywordParam {
    private String keyword;

    @JsonCreator
    public KeywordParam(@JsonProperty("keyword") String keyword) {
        this.keyword = keyword;
    }
}
