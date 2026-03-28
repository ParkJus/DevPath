package com.devpath.api.admin.dto.governance;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SystemPolicyResponse {

    private Integer platformFeeRate;
    private Integer refundPolicyDays;
    private Long maxCoursePrice;
    private LocalDateTime updatedAt;
}