package com.github.antksk.kakaopay.tasks.task2.controller.json;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
@EqualsAndHashCode
public class ProgramIntroductionCountJson {
    private final String region;
    @EqualsAndHashCode.Exclude
    private final int count;

    public ProgramIntroductionCountJson(String region, int count) {
        this.region = region;
        this.count = count;
    }



    public static ProgramIntroductionCountJson of( String region, int count ){
        return new ProgramIntroductionCountJson(region, count);
    }
}
