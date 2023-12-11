package com.example.studentcounselingsystem.counseling.controller;

import com.example.studentcounselingsystem.counseling.dto.request.CounselingRequest;
import com.example.studentcounselingsystem.counseling.dto.request.FeedbackRequest;
import com.example.studentcounselingsystem.counseling.entity.Counseling;
import com.example.studentcounselingsystem.counseling.service.CounselingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
}
