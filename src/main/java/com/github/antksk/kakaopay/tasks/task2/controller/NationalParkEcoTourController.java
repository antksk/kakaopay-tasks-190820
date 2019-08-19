package com.github.antksk.kakaopay.tasks.task2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NationalParkEcoTourController {

    @GetMapping("/task2")
    public String test(){
        return "test";
    }

}
