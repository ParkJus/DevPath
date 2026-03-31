package com.devpath.api.instructor.controller;

import com.devpath.api.instructor.dto.marketing.ConversionResponse;
import com.devpath.api.instructor.dto.marketing.CouponCreateRequest;
import com.devpath.api.instructor.dto.marketing.CouponResponse;
import com.devpath.api.instructor.dto.marketing.PromotionCreateRequest;
import com.devpath.api.instructor.dto.marketing.PromotionStatusUpdateRequest;
import com.devpath.api.instructor.service.InstructorMarketingService;
import com.devpath.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Instructor - Marketing", description = "강사 마케팅 도구 API")
@RestController
@RequestMapping("/api/instructor/marketing")
@RequiredArgsConstructor
public class InstructorMarketingController {

    private final InstructorMarketingService instructorMarketingService;

    // 쿠폰 생성 결과는 생성된 코드와 설정값을 그대로 응답한다.
    @Operation(summary = "할인 쿠폰 발행")
    @PostMapping("/coupons")
    public ApiResponse<CouponResponse> createCoupon(
            @RequestBody @Valid CouponCreateRequest request,
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId
    ) {
        return ApiResponse.success(
                "쿠폰이 발행되었습니다.",
                instructorMarketingService.createCoupon(userId, request)
        );
    }

    // 프로모션 등록은 쓰기 작업만 수행하고 본문 응답은 비운다.
    @Operation(summary = "타임세일/프로모션 등록")
    @PostMapping("/promotions")
    public ApiResponse<Void> createPromotion(
            @RequestBody @Valid PromotionCreateRequest request,
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId
    ) {
        instructorMarketingService.createPromotion(userId, request);
        return ApiResponse.success("프로모션이 등록되었습니다.", null);
    }

    // 강의 홍보 상태 변경은 강사 본인 강의의 최신 프로모션만 대상으로 한다.
    @Operation(summary = "강의 홍보 상태 변경")
    @PatchMapping("/courses/{courseId}/promotion-status")
    public ApiResponse<Void> updatePromotionStatus(
            @PathVariable Long courseId,
            @RequestBody @Valid PromotionStatusUpdateRequest request,
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId
    ) {
        instructorMarketingService.updatePromotionStatus(courseId, userId, request);
        return ApiResponse.success("홍보 상태가 변경되었습니다.", null);
    }

    // 전환 통계는 이번 주차 범위에서 전체 요약과 강의별 최신 집계만 내려준다.
    @Operation(summary = "유입/전환 통계 조회")
    @GetMapping("/conversions")
    public ApiResponse<ConversionResponse> getConversions(
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId
    ) {
        return ApiResponse.success(
                "전환 통계를 조회했습니다.",
                instructorMarketingService.getConversions(userId)
        );
    }
}
