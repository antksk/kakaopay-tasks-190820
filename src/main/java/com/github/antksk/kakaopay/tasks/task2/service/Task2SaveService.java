package com.github.antksk.kakaopay.tasks.task2.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.antksk.kakaopay.tasks.task2.entity.FormalServiceRegion;
import com.github.antksk.kakaopay.tasks.task2.entity.NationalParkEcoTour;
import com.github.antksk.kakaopay.tasks.task2.entity.ServiceRegion;
import com.github.antksk.kakaopay.tasks.task2.repository.NationalParkEcoTourRepository;
import com.github.antksk.kakaopay.tasks.task2.repository.ServiceRegionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Task2SaveService {

    private final NationalParkEcoTourRepository nationalParkEcoTourRepository;
    private final ServiceRegionRepository serviceRegionRepository;

    private final ObjectMapper objectMapper;

    @Qualifier("komoranKeywordService")
    private final KeywordService keywordService;

    // 데이터 파일에서 각 레코드를 데이터베이스에 저장(추가/수정 포함)
    public NationalParkEcoTour save(NationalParkEcoTour nationalParkEcoTour){
        return nationalParkEcoTourRepository.save(nationalParkEcoTour);
    }

    public List<NationalParkEcoTour> save(Iterable<NationalParkEcoTour> nationalParkEcoTours){
        return nationalParkEcoTourRepository.saveAll(nationalParkEcoTours);
    }

    // 생태 관공 정보 데이터 조회
    @Transactional(readOnly = true)
    public Page<NationalParkEcoTour> findByFormalServiceRegion(FormalServiceRegion formalServiceRegion, Pageable pageable){
        return nationalParkEcoTourRepository.findByFormalServiceRegion(formalServiceRegion, pageable);
    }

    @Transactional(readOnly = true)
    public FormalServiceRegion convertFormalServiceRegion(Optional<String> optionalFormalRegion){
        return optionalFormalRegion /** CSV 파일에서 서비스 지역 정보를 찾음 */
            .map(serviceRegionRepository::findFirstByRegionContainingOrderByCode)
            /** 행정구역 정보에서 찾은 지역 정보와 맞는 경우 */
            .filter(Optional::isPresent)
            .map(Optional::get)
            /** 행정구역 형태의 서비스 지역정보를 전달 받음 */
            .map(ServiceRegion::getFormalServiceRegion)
            .orElse(FormalServiceRegion.empty());
    }


    private static final String DEFAULT_ARRAY_JSON = "[]";

    // Keyword 서비스를 통해 검색 키워드
    public String programIntroductionDetailsKeywordJson( String programIntroductionDetails ){

        if(StringUtils.isEmpty(programIntroductionDetails)) return DEFAULT_ARRAY_JSON;

        try {
            return objectMapper.writeValueAsString(keywordService.analyze(programIntroductionDetails));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return DEFAULT_ARRAY_JSON;
    }

}

