package com.github.antksk.kakaopay.tasks.task2.service;

import java.util.List;
import java.util.Map;
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
import com.github.antksk.kakaopay.tasks.task2.controller.json.ProgramIntroductionCountJson;
import com.github.antksk.kakaopay.tasks.task2.controller.json.ProgramPageDecorator;
import com.github.antksk.kakaopay.tasks.task2.entity.FormalServiceRegion;
import com.github.antksk.kakaopay.tasks.task2.entity.NationalParkEcoTour;
import com.github.antksk.kakaopay.tasks.task2.entity.ServiceRegion;
import com.github.antksk.kakaopay.tasks.task2.repository.NationalParkEcoTourRepository;
import com.github.antksk.kakaopay.tasks.task2.repository.ServiceRegionRepository;

import lombok.RequiredArgsConstructor;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toSet;

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
    public ProgramPageDecorator<NationalParkEcoTour> findByRegion(String region, Pageable pageable){
        return new ProgramPageDecorator<NationalParkEcoTour>(nationalParkEcoTourRepository.findByRegionContaining(region, pageable));
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


    // 프로그램 소개 컬럼의 특정 문자열 포함된 레코드에서 서비스 지역 개수를 세어 출력
    public Set<ProgramIntroductionCountJson> programIntroductionCount(final String keyword){
        return nationalParkEcoTourRepository.findByProgramIntroductionContaining(keyword)
            .stream()
            .collect(
                groupingBy(
                    NationalParkEcoTour::getFormalServiceRegion,
                    summingInt(e -> e.programIntroductionCount(keyword)))
            )
            .entrySet()
                 .stream().map(e->ProgramIntroductionCountJson.of(e.getKey().getRegion(),e.getValue()))
            .collect(toSet());
    }

    // 모든 레코드에 프로그램 상세 정보를 읽어와서 입력 단어의 출현빈도수를 계산
    public int programIntroductionCountDetail(final String keyword){
        return nationalParkEcoTourRepository.findByKeywordJsonContaining(keyword)
                                            .stream()
                                            .mapToInt(e -> e.programIntroductionDetailCount(keyword))
                                            .sum();
    }

}

