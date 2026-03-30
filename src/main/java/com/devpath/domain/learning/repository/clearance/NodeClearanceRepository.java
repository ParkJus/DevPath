package com.devpath.domain.learning.repository.clearance;

import com.devpath.domain.learning.entity.clearance.ClearanceStatus;
import com.devpath.domain.learning.entity.clearance.NodeClearance;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NodeClearanceRepository extends JpaRepository<NodeClearance, Long> {

    Optional<NodeClearance> findByUserIdAndNodeNodeId(Long userId, Long nodeId);

    List<NodeClearance> findAllByUserIdAndNodeRoadmapRoadmapIdOrderByNodeSortOrderAscNodeNodeIdAsc(
            Long userId,
            Long roadmapId
    );

    List<NodeClearance> findAllByUserIdOrderByLastCalculatedAtDesc(Long userId);

    List<NodeClearance> findAllByUserIdAndClearanceStatusOrderByClearedAtDesc(
            Long userId,
            ClearanceStatus clearanceStatus
    );

    long countByUserIdAndClearanceStatus(Long userId, ClearanceStatus clearanceStatus);

    @Query("""
            select nc
            from NodeClearance nc
            join fetch nc.user u
            join fetch nc.node n
            where u.id = :learnerId
              and nc.clearanceStatus = com.devpath.domain.learning.entity.clearance.ClearanceStatus.NOT_CLEARED
            order by nc.lastCalculatedAt desc
            """)
    List<NodeClearance> findInProgressClearancesByUserId(@Param("learnerId") Long learnerId);

    @Query("""
            select nc
            from NodeClearance nc
            join fetch nc.user u
            join fetch nc.node n
            where u.id <> :learnerId
              and u.isActive = true
              and n.nodeId in :nodeIds
              and nc.clearanceStatus = com.devpath.domain.learning.entity.clearance.ClearanceStatus.NOT_CLEARED
            order by nc.lastCalculatedAt desc
            """)
    List<NodeClearance> findCandidateInProgressClearances(
            @Param("learnerId") Long learnerId,
            @Param("nodeIds") Collection<Long> nodeIds
    );
}
