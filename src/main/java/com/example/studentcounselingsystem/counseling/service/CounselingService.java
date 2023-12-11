package com.example.studentcounselingsystem.counseling.service;

import com.example.studentcounselingsystem.counseling.dto.request.CounselingRequest;
import com.example.studentcounselingsystem.counseling.entity.Counseling;
import com.example.studentcounselingsystem.counseling.repository.CounselingRepository;
import com.example.studentcounselingsystem.student.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CounselingService {
    private final CounselingRepository counselingRepository;
    private final StudentService studentService;

    public Counseling createCounseling(CounselingRequest counselingRequest) {
        if (!this.isExistStudentById(counselingRequest.getStudentId())) {
            throw new EntityNotFoundException("존재하지 않는 학생입니다.");
        }
        Counseling counseling = Counseling.builder()
                .studentId(counselingRequest.getStudentId())
                .content(counselingRequest.getContent())
                .createdDate(LocalDateTime.now())
                .build();
        return counselingRepository.save(counseling);
    }

    public boolean isExistStudentById(UUID studentId) {
        return studentService.isExistStudentById(studentId);
    }
}
