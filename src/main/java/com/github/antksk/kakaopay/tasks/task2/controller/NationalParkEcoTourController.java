package com.github.antksk.kakaopay.tasks.task2.controller;


import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.antksk.kakaopay.tasks.task2.controller.json.ProgramIntroductionCountDetailJson;
import com.github.antksk.kakaopay.tasks.task2.controller.json.ProgramIntroductionCountJson;
import com.github.antksk.kakaopay.tasks.task2.controller.json.ProgramPageDecorator;
import com.github.antksk.kakaopay.tasks.task2.entity.NationalParkEcoTour;
import com.github.antksk.kakaopay.tasks.task2.service.Task2SaveService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/task2")
public class NationalParkEcoTourController {

    private final Task2SaveService task2SaveService;

    @PostMapping("/region")
    public ProgramPageDecorator<NationalParkEcoTour> region(@RequestBody  RegionParam regionParam, Pageable pageable){
        return task2SaveService.findByRegion(regionParam.getRegion(), pageable);
    }

    @PostMapping("/program/introduction")
    public Set<ProgramIntroductionCountJson> introduction(@RequestBody  KeywordParam keywordParam){
        return task2SaveService.programIntroductionCount(keywordParam.getKeyword());
    }

    @PostMapping("/program/introduction/detail")
    public ProgramIntroductionCountDetailJson introductionDetail(@RequestBody KeywordParam keywordParam){
        final String keyword = keywordParam.getKeyword();
        return ProgramIntroductionCountDetailJson.of(keyword, task2SaveService.programIntroductionCountDetail(keyword));
    }

    @PostMapping("/save")
    public NationalParkEcoTour save(@RequestBody NationalParkEcoTour  nationalParkEcoTour){
        return task2SaveService.save(nationalParkEcoTour);
    }
}
