package com.github.antksk.kakaopay.tasks.task2.controller.json;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProgramPageDecorator<T> {
    private final Page<T> page;

    public ProgramPageDecorator(Page<T> page) {
        this.page = page;
    }

    @JsonProperty("programs")
    public List<T> getContent() {
        return this.page.getContent();
    }

    public int getTotalPages() {
        return page.getTotalPages();
    }

    public long getTotalElements() {
        return page.getTotalElements();
    }
}
