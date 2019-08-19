package com.github.antksk.kakaopay.tasks.task2.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.github.antksk.kakaopay.tasks.task2.entity.FormalServiceRegion;
import com.github.antksk.kakaopay.tasks.task2.entity.NationalParkEcoTour;

public interface NationalParkEcoTourRepository extends JpaRepository<NationalParkEcoTour, Long> {

    // 프로그램 소개 조회
    Page<NationalParkEcoTour> findByProgramIntroductionContaining(String keyword, Pageable pageable);
    Set<NationalParkEcoTour> findByProgramIntroductionContaining(String keyword);


    // 프로그램 소개 상세 키워드 조회
    Page<NationalParkEcoTour> findByKeywordJsonContaining(String keyword, Pageable pageable);
    Set<NationalParkEcoTour> findByKeywordJsonContaining(String keyword);

    // 서비스 지역 코드를 통한 조회
    Page<NationalParkEcoTour> findByFormalServiceRegion(FormalServiceRegion formalServiceRegion, Pageable pageable);


}

