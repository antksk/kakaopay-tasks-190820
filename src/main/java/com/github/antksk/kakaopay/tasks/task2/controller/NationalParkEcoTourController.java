package com.github.antksk.kakaopay.tasks.task2.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class NationalParkEcoTourController {

    @PostMapping("/task2/region")
    public RegionParam region(@RequestBody RegionParam regionParam){
        log.debug("{}", regionParam);
        return regionParam;
    }

}
