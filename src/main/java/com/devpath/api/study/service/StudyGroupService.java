package com.devpath.api.study.service;

import com.devpath.api.study.dto.StudyGroupRequest;
import com.devpath.api.study.dto.StudyGroupResponse;
import com.devpath.domain.study.entity.StudyGroup;
import com.devpath.domain.study.entity.StudyGroupStatus;
import com.devpath.domain.study.repository.StudyGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 기본적으로 읽기 전용 트랜잭션 적용
public class StudyGroupService {

    private final StudyGroupRepository studyGroupRepository;

    @Transactional // 쓰기 작업이므로 개별적으로 트랜잭션 오버라이딩
    public StudyGroupResponse createStudyGroup(StudyGroupRequest request) {
        StudyGroup studyGroup = StudyGroup.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(StudyGroupStatus.RECRUITING)
                .maxMembers(request.getMaxMembers())
                .build();

        StudyGroup savedGroup = studyGroupRepository.save(studyGroup);
        return StudyGroupResponse.from(savedGroup);
    }

    public StudyGroupResponse getStudyGroup(Long id) {
        StudyGroup studyGroup = studyGroupRepository.findById(id)
                // TODO: 추후 ErrorCode.STUDY_GROUP_NOT_FOUND 등으로 커스텀 예외 연동
                .orElseThrow(() -> new IllegalArgumentException("해당 스터디 그룹을 찾을 수 없습니다."));

        return StudyGroupResponse.from(studyGroup);
    }

    public List<StudyGroupResponse> getAllStudyGroups() {
        return studyGroupRepository.findAll().stream()
                .filter(group -> !group.getIsDeleted()) // Soft Delete된 데이터 제외
                .map(StudyGroupResponse::from)
                .collect(Collectors.toList());
    }
}