package com.github.antksk.kakaopay.tasks.task2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;

@Configuration
public class Task2Config {

    @Bean
    public Komoran komoran(){
        return new Komoran(DEFAULT_MODEL.LIGHT);
    }

}
