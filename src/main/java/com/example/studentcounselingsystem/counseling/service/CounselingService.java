package com.example.studentcounselingsystem.counseling.service;

import com.example.studentcounselingsystem.counseling.dto.request.CreateCounselingRequest;
import com.example.studentcounselingsystem.counseling.dto.request.CounselingSearchRequest;
import com.example.studentcounselingsystem.counseling.dto.request.CreateFeedbackRequest;
import com.example.studentcounselingsystem.counseling.dto.response.PageCounselingResponse;
import com.example.studentcounselingsystem.counseling.entity.Counseling;
import com.example.studentcounselingsystem.counseling.exception.AlreadyCreatedFeedbackException;
import com.example.studentcounselingsystem.counseling.repository.CounselingRepository;
import com.example.studentcounselingsystem.counseling.repository.CounselingSpecs;
import com.example.studentcounselingsystem.employee.service.EmployeeService;
import com.example.studentcounselingsystem.student.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CounselingService {
    private final CounselingRepository counselingRepository;
    private final StudentService studentService;
    private final EmployeeService employeeService;

    /**
     * 상담 내역 등록
     * @param createCounselingRequest 상담 DTO
     * @return Counseling
     */
    @Transactional
    public Counseling createCounseling(CreateCounselingRequest createCounselingRequest) {
        if (this.studentService.getStudentById(createCounselingRequest.getStudentId()) == null) {
            throw new EntityNotFoundException("존재하지 않는 학생입니다.");
        }
        Counseling counseling = Counseling.builder()
                .studentId(createCounselingRequest.getStudentId())
                .content(createCounselingRequest.getContent())
                .createdDate(LocalDateTime.now())
                .build();
        return counselingRepository.save(counseling);
    }

    /**
     * 해당 상담의 피드백 등록
     * @param createFeedbackRequest 피드백 DTO
     * @return Counseling
     */
    @Transactional
    public Counseling updateFeedback(CreateFeedbackRequest createFeedbackRequest, int id) {
        Counseling counseling = this.counselingRepository.findById(id);
        if (counseling == null) {
            throw new EntityNotFoundException("존재하지 않는 상담입니다.");
        }
        if (counseling.getFeedback() != null) {
            throw new AlreadyCreatedFeedbackException("이미 처리된 피드백 입니다.");
        }
        counseling.setEmployeeId(createFeedbackRequest.getEmployeeId());
        counseling.setFeedback(createFeedbackRequest.getFeedback());
        // 피드백이 등록 되었다면 IsRead 값이 True 일 수 밖에 없음
        counseling.setIsRead(true);
        return counselingRepository.save(counseling);
    }

    /**
     * 상담 상세 조회
     * @param id 상담 아이디
     * @return 상담
     */
    @Transactional
    public Counseling getCounseling(int id) {
        return counselingRepository.findById(id);
    }

    /**
     * 상담 상세 조회(담당자 ID 포함)
     * @param id 상담 아이디
     * @param employeeId 담당자 아이디
     * @return 상담
     */
    @Transactional
    public Counseling getCounseling(int id, int employeeId) {
        if (employeeService.getEmployeeById(employeeId) == null) {
            throw new EntityNotFoundException("존재하지 않는 담당자 아이디 입니다.");
        }
        Counseling counseling = counselingRepository.findById(id);
        counseling.setIsRead(true);
        return counselingRepository.save(counseling);
    }

    /**
     * 상담 목록 조회
     * @param pageNo 페이지네이션 Number
     * @param criteria 정렬 기준
     * @param sort 정렬 방식
     * @param counselingSearchRequest 상담 검색 옵션 DTO
     * @return 상담 목록
     */
    @Transactional
    public PageCounselingResponse getCounselingList(int pageNo, String criteria, String sort, CounselingSearchRequest counselingSearchRequest) {
        // 페이지 네이션 선언
        Pageable pageable = (sort.equals("ASC")) ?
                PageRequest.of(pageNo, 10, Sort.by(Sort.Direction.ASC, criteria))
                : PageRequest.of(pageNo, 10, Sort.by(Sort.Direction.DESC, criteria));

        // JPA Specification 조건별 분기
        Specification<Counseling> spec = (root, query, criteriaBuilder) -> null;
        if (counselingSearchRequest.getStudentId() != null) {
            spec = spec.and(CounselingSpecs.withStudentId(counselingSearchRequest.getStudentId()));
        }
        if (counselingSearchRequest.getEmployeeId() != null) {
            spec = spec.and(CounselingSpecs.withEmployeeId(counselingSearchRequest.getEmployeeId()));
        }
        if (counselingSearchRequest.getIsRead() != null) {
            spec = spec.and(CounselingSpecs.withIsRead(counselingSearchRequest.getIsRead()));
        }
        if (counselingSearchRequest.getIsFeedback() != null) {
            spec = spec.and(CounselingSpecs.withIsFeedback());
        }
        return new PageCounselingResponse(counselingRepository.findAll(spec, pageable));
    }
}