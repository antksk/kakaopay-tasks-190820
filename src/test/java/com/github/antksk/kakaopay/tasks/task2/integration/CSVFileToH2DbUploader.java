package com.github.antksk.kakaopay.tasks.task2.integration;

import java.util.List;
import java.util.Optional;

import com.github.antksk.kakaopay.tasks.task2.entity.FormalServiceRegion;
import com.github.antksk.kakaopay.tasks.task2.entity.NationalParkEcoTour;
import com.github.antksk.kakaopay.tasks.task2.entity.ServiceRegion;
import com.github.antksk.kakaopay.tasks.task2.entity.ToEntity;
import com.github.antksk.kakaopay.tasks.task2.integration.csv.AdministrativeDistrictCsvFile;
import com.github.antksk.kakaopay.tasks.task2.integration.csv.NationalParkEcoTourCsvFile;
import com.github.antksk.kakaopay.tasks.task2.repository.ServiceRegionRepository;
import com.github.antksk.kakaopay.tasks.task2.service.Task2SaveService;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CSVFileToH2DbUploader {


    public static void serviceRegion(final ServiceRegionRepository serviceRegionRepository){
        List<ServiceRegion> districts = AdministrativeDistrictCsvFile.load()
                                                                     .stream().map(AdministrativeDistrictCsvFile::entity)
                                                                     .collect(toImmutableList());

        serviceRegionRepository.saveAll(districts);
    }

    public static void nationalParkEcoTour(final Task2SaveService task2SaveService){
        final List<NationalParkEcoTour> nationalParkEcoTours = NationalParkEcoTourCsvFile.load().stream().map(ToEntity::entity).collect(toList());

        for (NationalParkEcoTour nationalParkEcoTour : nationalParkEcoTours) {
            // 행정 구역형태의 지역 정보로 변경
            Optional<String> optionalFormalRegion = nationalParkEcoTour.formalRegion();
            FormalServiceRegion formalServiceRegion = task2SaveService.convertFormalServiceRegion(optionalFormalRegion);
            nationalParkEcoTour.setFormalServiceRegion(formalServiceRegion);
            nationalParkEcoTour.setKeywordJson(task2SaveService.programIntroductionDetailsKeywordJson(nationalParkEcoTour.getProgramIntroductionDetails()));
        }

        task2SaveService.save(nationalParkEcoTours);
    }
}
