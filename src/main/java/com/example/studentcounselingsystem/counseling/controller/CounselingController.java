package com.example.studentcounselingsystem.counseling.controller;

import com.example.studentcounselingsystem.counseling.dto.request.CounselingRequest;
import com.example.studentcounselingsystem.counseling.dto.request.CounselingSearchRequest;
import com.example.studentcounselingsystem.counseling.dto.request.FeedbackRequest;
import com.example.studentcounselingsystem.counseling.entity.Counseling;
import com.example.studentcounselingsystem.counseling.service.CounselingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CounselingController {
    private final CounselingService counselingService;

    @PostMapping("counseling")
    public ResponseEntity<Counseling> createCounseling(@Valid @RequestBody CounselingRequest counselingRequest) {
        return ResponseEntity.ok(counselingService.createCounseling(counselingRequest));
    }

    @PostMapping("counseling/feedback")
    public ResponseEntity<Counseling> updateCounselingFeedback(@Valid @RequestBody FeedbackRequest feedbackRequest) {
        return ResponseEntity.ok(counselingService.updateFeedback(feedbackRequest));
    }

    @GetMapping("counseling/{id}")
    public ResponseEntity<Counseling> getCounseling(@PathVariable() int id,
                                                    @RequestParam(required = false) Integer counselorId) {
        if (counselorId == null) {
            return ResponseEntity.ok(counselingService.getCounseling(id));
        }
        return ResponseEntity.ok(counselingService.getCounseling(id, counselorId));
    }

    @GetMapping("counseling/list")
    public ResponseEntity<Page<Counseling>> getCounselingList(@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
                                                              @RequestParam(required = false, defaultValue = "createdDate", value = "orderby") String criteria,
                                                              @RequestParam(required = false, defaultValue = "DESC", value = "sort") String sort,
                                                              CounselingSearchRequest counselingSearchRequest) {
        return ResponseEntity.ok(counselingService.getCounselingList(pageNo, criteria, sort, counselingSearchRequest));
    }
}
