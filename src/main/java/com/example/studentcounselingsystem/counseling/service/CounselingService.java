package com.example.studentcounselingsystem.counseling.service;

import com.example.studentcounselingsystem.counseling.dto.request.CounselingRequest;
import com.example.studentcounselingsystem.counseling.dto.request.FeedbackRequest;
import com.example.studentcounselingsystem.counseling.entity.Counseling;
import com.example.studentcounselingsystem.counseling.exception.AlreadyCreatedFeedbackException;
import com.example.studentcounselingsystem.counseling.repository.CounselingRepository;
import com.example.studentcounselingsystem.employee.service.EmployeeService;
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
    private final EmployeeService employeeService;

    /**
     * 상담 내역을 등록
     * @param counselingRequest: 상담 DTO
     * @return Counseling
     */
    public Counseling createCounseling(CounselingRequest counselingRequest) {
        if (!this.studentService.isExistStudentById(counselingRequest.getStudentId())) {
            throw new EntityNotFoundException("존재하지 않는 학생입니다.");
        }
        Counseling counseling = Counseling.builder()
                .studentId(counselingRequest.getStudentId())
                .content(counselingRequest.getContent())
                .createdDate(LocalDateTime.now())
                .build();
        return counselingRepository.save(counseling);
    }

    /**
     * 해당 상담의 피드백을 등록
     * @param feedbackRequest: 피드백 DTO
     * @return Counseling
     */
    public Counseling updateFeedback(FeedbackRequest feedbackRequest) {
        Counseling counseling = this.counselingRepository.findById(feedbackRequest.getId());
        if (counseling == null) {
            throw new EntityNotFoundException("존재하지 않는 상담입니다.");
        }
        if (counseling.getFeedback() != null) {
            throw new AlreadyCreatedFeedbackException("이미 처리된 피드백 입니다.");
        }
        counseling.setCounselorId(feedbackRequest.getCounselorId());
        counseling.setFeedback(feedbackRequest.getFeedback());
        // 피드백이 등록 되었다면 IsRead 값이 True 일 수 밖에 없음
        counseling.setIsRead(true);
        return counselingRepository.save(counseling);
    }

    public Counseling getCounseling(UUID id) {
        return counselingRepository.findById(id);
    }

    public Counseling getCounseling(UUID id, UUID counselorId) {
        if (employeeService.findById(counselorId) == null) {
            throw new EntityNotFoundException("존재하지 않는 직원 아이디 입니다.");
        }
        Counseling counseling = counselingRepository.findById(id);
        counseling.setIsRead(true);
        return counselingRepository.save(counseling);
    }
}
