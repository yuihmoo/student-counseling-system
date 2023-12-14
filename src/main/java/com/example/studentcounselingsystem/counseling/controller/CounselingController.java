package com.example.studentcounselingsystem.counseling.controller;

import com.example.studentcounselingsystem.common.dto.response.CustomErrorResponse;
import com.example.studentcounselingsystem.counseling.dto.request.CreateCounselingRequest;
import com.example.studentcounselingsystem.counseling.dto.request.CounselingSearchRequest;
import com.example.studentcounselingsystem.counseling.dto.request.CreateFeedbackRequest;
import com.example.studentcounselingsystem.counseling.dto.response.PageCounselingResponse;
import com.example.studentcounselingsystem.counseling.entity.Counseling;
import com.example.studentcounselingsystem.counseling.service.CounselingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "CounselingController", description = "상담 컨트롤러")
@RestController
@RequiredArgsConstructor
public class CounselingController {
    private final CounselingService counselingService;

    @Operation(operationId = "createCounseling", summary = "상담 등록", description = "학생용 상담 등록 API", tags = {
            "CounselingController"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "상담 등록 성공", content = @Content(schema = @Schema(implementation = Counseling.class))),
            @ApiResponse(responseCode = "400", description = "상담 내용 검증 오류", content = @Content(schema = @Schema(implementation = CustomErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 학생", content = @Content(schema = @Schema(implementation = CustomErrorResponse.class)))
    })
    @PostMapping("counseling")
    public ResponseEntity<Counseling> createCounseling(@Valid @RequestBody CreateCounselingRequest createCounselingRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(counselingService.createCounseling(createCounselingRequest));
    }

    @Operation(operationId = "createCounseling", summary = "상담 피드백 등록", description = "담당자 상담 피드백 등록 API", tags = {
            "CounselingController"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "상담 피드백 등록 성공", content = @Content(schema = @Schema(implementation = Counseling.class))),
            @ApiResponse(responseCode = "400", description = "상담 피드백 검증 오류", content = @Content(schema = @Schema(implementation = CustomErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "이미 처리된 피드백", content = @Content(schema = @Schema(implementation = CustomErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 상담", content = @Content(schema = @Schema(implementation = CustomErrorResponse.class)))
    })
    @PostMapping("counseling/{id}/feedback")
    public ResponseEntity<Counseling> updateCounselingFeedback(
            @PathVariable() @Parameter(description = "상담 아이디", example = "1") int id,
            @Valid @RequestBody CreateFeedbackRequest createFeedbackRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(counselingService.updateFeedback(createFeedbackRequest, id));
    }

    @Operation(operationId = "createCounseling", summary = "상담 조회", description = "상담 상세 조회 API", tags = {
            "CounselingController"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상담 조회 성공", content = @Content(schema = @Schema(implementation = Counseling.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 담당자", content = @Content(schema = @Schema(implementation = CustomErrorResponse.class)))
    })
    @GetMapping("counseling/{id}")
    public ResponseEntity<Counseling> getCounseling(
            @PathVariable() @Parameter(description = "상담 아이디", example = "1") int id,
            @RequestParam(required = false) @Parameter(description = "담당자 아이디", example = "1") Integer employeeId) {
        if (employeeId == null) {
            return ResponseEntity.ok(counselingService.getCounseling(id));
        }
        return ResponseEntity.ok(counselingService.getCounseling(id, employeeId));
    }

    @Operation(operationId = "createCounseling", summary = "상담 목록 조회", description = "상담 목록을 조건별로 조회 API", tags = {
            "CounselingController"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상담 목록 조회 성공", content = @Content(schema = @Schema(implementation = PageCounselingResponse.class))),
    })
    @GetMapping("counseling/list")
    public ResponseEntity<PageCounselingResponse> getCounselingList(
            @RequestParam(required = false, defaultValue = "0", value = "page") @Parameter(description = "현재 페이지", example = "1") int pageNo,
            @RequestParam(required = false, defaultValue = "createdDate", value = "orderby") @Parameter(description = "정렬 파라미터", example = "createDate") String criteria,
            @RequestParam(required = false, defaultValue = "DESC", value = "sort") @Parameter(description = "정렬 방식", example = "DESC") String sort,
            CounselingSearchRequest counselingSearchRequest) {
        return ResponseEntity.ok(counselingService.getCounselingList(pageNo, criteria, sort, counselingSearchRequest));
    }
}
