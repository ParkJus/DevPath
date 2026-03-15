package com.devpath.api.admin.service;

import com.devpath.api.admin.dto.PolicyGovernanceRequests.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminPolicyAndMappingService {

    // 1. 강의-노드 매핑 후보 조회
    @Transactional(readOnly = true)
    public Object getMappingCandidates() {
        // TODO: 태그를 비교 분석하여 (Course의 태그와 Node의 태그 일치율) 매핑 후보를 추천하는 로직 추가
        return Collections.emptyList();
    }

    // 2. 강의-노드 매핑 확정 저장
    public void updateCourseNodeMapping(Long courseId, UpdateNodeMapping request) {
        // TODO: 관리자가 검토 후 확정한 노드 ID들을 강의와 매핑 (CourseNodeMapping 테이블 등에 저장)
    }

    // 3. 시스템 운영 정책 조회
    @Transactional(readOnly = true)
    public Object getSystemPolicies() {
        // TODO: devpath.sql의 'system_settings' 테이블에서 현재 정책값들을 불러오는 로직
        return null;
    }

    // 4. 시스템 운영 정책 수정
    public void updateSystemPolicies(UpdateSystemPolicy request) {
        // TODO: 수수료율 등의 설정값을 'system_settings' 테이블에 덮어쓰기 (또는 이력 저장)
    }

    // 5. 스트리밍 정책 수정
    public void updateStreamingPolicy(UpdateStreamingPolicy request) {
        // TODO: HLS 영상 암호화 여부, 동접 제한 수 등 보안 정책 설정 업데이트
    }
}