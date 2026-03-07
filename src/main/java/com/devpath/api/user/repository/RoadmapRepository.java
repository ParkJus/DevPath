package com.devpath.api.user.repository;

import com.devpath.domain.roadmap.entity.Roadmap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoadmapRepository extends JpaRepository<Roadmap, Long> {
    Optional<Roadmap> findByRoadmapIdAndIsDeletedFalse(Long roadmapId);
}