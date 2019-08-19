package com.github.antksk.kakaopay.tasks.task2.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.antksk.kakaopay.tasks.task2.entity.ServiceRegion;

@Repository
public interface ServiceRegionRepository extends JpaRepository<ServiceRegion, String> {

    Set<ServiceRegion> findByRegionContainingOrderByCode(String region);

    Optional<ServiceRegion> findFirstByRegionContainingOrderByCode(String region);
}
