package com.example.studentcounselingsystem.counseling.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.studentcounselingsystem.common.dto.response.CustomErrorResponse;
import com.example.studentcounselingsystem.counseling.dto.request.CounselingRequest;
import com.example.studentcounselingsystem.counseling.dto.request.CounselingSearchRequest;
import com.example.studentcounselingsystem.counseling.dto.request.FeedbackRequest;
import com.example.studentcounselingsystem.counseling.entity.Counseling;
import com.example.studentcounselingsystem.counseling.service.CounselingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "CounselingController", description = "상담 컨트롤러")
@RestController
@RequiredArgsConstructor
public class CounselingController {
	private final CounselingService counselingService;

	@Operation(operationId = "createCounseling", summary = "상담 등록", description = "학생용 상담 등록 API", tags = {
		"CounselingController"})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "상담 등록 성공", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Counseling.class)))),
		@ApiResponse(responseCode = "400", description = "상담 내용 검증 오류", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 학생", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class))))
	})
	@PostMapping("counseling")
	public ResponseEntity<Counseling> createCounseling(@Valid @RequestBody CounselingRequest counselingRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(counselingService.createCounseling(counselingRequest));
	}

	@PostMapping("counseling/{id}/feedback")
	public ResponseEntity<Counseling> updateCounselingFeedback(
		@PathVariable() int id,
		@Valid @RequestBody FeedbackRequest feedbackRequest) {
		return ResponseEntity.ok(counselingService.updateFeedback(feedbackRequest, id));
	}

	@GetMapping("counseling/{id}")
	public ResponseEntity<Counseling> getCounseling(
		@PathVariable() int id,
		@RequestParam(required = false) Integer counselorId) {
		if (counselorId == null) {
			return ResponseEntity.ok(counselingService.getCounseling(id));
		}
		return ResponseEntity.ok(counselingService.getCounseling(id, counselorId));
	}

	@GetMapping("counseling/list")
	public ResponseEntity<Page<Counseling>> getCounselingList(
		@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
		@RequestParam(required = false, defaultValue = "createdDate", value = "orderby") String criteria,
		@RequestParam(required = false, defaultValue = "DESC", value = "sort") String sort,
		CounselingSearchRequest counselingSearchRequest) {
		return ResponseEntity.ok(counselingService.getCounselingList(pageNo, criteria, sort, counselingSearchRequest));
	}
}
