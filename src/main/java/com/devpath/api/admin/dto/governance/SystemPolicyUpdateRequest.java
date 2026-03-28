package com.devpath.api.admin.dto.governance;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SystemPolicyUpdateRequest {

    private Integer platformFeeRate;
    private Integer refundPolicyDays;
    private Long maxCoursePrice;
}