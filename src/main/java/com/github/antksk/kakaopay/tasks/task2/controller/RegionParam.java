package com.github.antksk.kakaopay.tasks.task2.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

@Value
public class RegionParam {
    private String region;

    @JsonCreator
    public RegionParam(@JsonProperty("region") String region) {
        this.region = region;
    }
}
