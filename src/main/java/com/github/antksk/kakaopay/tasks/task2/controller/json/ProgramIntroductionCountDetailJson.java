package com.github.antksk.kakaopay.tasks.task2.controller.json;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProgramIntroductionCountDetailJson {
    private final String keyword;
    private final int count;

    public ProgramIntroductionCountDetailJson(String keyword, int count) {
        this.keyword = keyword;
        this.count = count;
    }

    public static ProgramIntroductionCountDetailJson of(String keyword, int count ){
        return new ProgramIntroductionCountDetailJson(keyword, count);
    }
}
