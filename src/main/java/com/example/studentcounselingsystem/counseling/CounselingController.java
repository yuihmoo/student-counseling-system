package com.example.studentcounselingsystem.counseling;

import com.example.studentcounselingsystem.counseling.dto.request.CounselingRequest;
import com.example.studentcounselingsystem.counseling.entity.Counseling;
import com.example.studentcounselingsystem.counseling.service.CounselingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CounselingController {
    private final CounselingService counselingService;

    @PostMapping("counseling")
    public ResponseEntity<Counseling> createCounseling(@RequestBody CounselingRequest counselingRequest) {
        return ResponseEntity.ok(counselingService.createCounseling(counselingRequest));
    }
}
