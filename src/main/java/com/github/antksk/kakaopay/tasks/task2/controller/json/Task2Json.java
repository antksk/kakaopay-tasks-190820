package com.github.antksk.kakaopay.tasks.task2.controller.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.antksk.kakaopay.tasks.task2.entity.FormalServiceRegion;

public interface Task2Json {

    @JsonProperty("prgm_name")
    String getProgramName();

    @JsonProperty("theme")
    String getTheme();

    @JsonIgnore
    String getProgramIntroduction();

    @JsonIgnore
    String getProgramIntroductionDetails();


    @JsonIgnore
    FormalServiceRegion getFormalServiceRegion();

    @JsonIgnore
    String getKeywordJson();

}
