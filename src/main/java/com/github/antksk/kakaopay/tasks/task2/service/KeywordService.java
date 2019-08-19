package com.github.antksk.kakaopay.tasks.task2.service;

import com.google.common.collect.ImmutableSet;

public interface KeywordService {
    ImmutableSet<String> analyze(String sentence);
}
