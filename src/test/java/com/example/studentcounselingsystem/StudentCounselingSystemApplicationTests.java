package com.example.studentcounselingsystem;

import com.example.studentcounselingsystem.counseling.dto.request.CounselingSearchRequest;
import com.example.studentcounselingsystem.counseling.dto.request.CreateCounselingRequest;
import com.example.studentcounselingsystem.counseling.dto.request.UpdateFeedbackRequest;
import com.example.studentcounselingsystem.counseling.dto.response.PageCounselingResponse;
import com.example.studentcounselingsystem.counseling.entity.Counseling;
import com.example.studentcounselingsystem.counseling.exception.AlreadyUpdatedFeedbackException;
import com.example.studentcounselingsystem.counseling.service.CounselingService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentCounselingSystemApplicationTests {
    @Autowired
    private CounselingService counselingService;

    @Test
    void contextLoads() {
    }

    @Test
    @Order(1)
    @DisplayName("Counseling-create-1: 상담 등록 성공")
    void createCounseling() {
        CreateCounselingRequest counselingRequest = new CreateCounselingRequest(1,
                "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        Counseling counseling = counselingService.createCounseling(counselingRequest);
        assertEquals(counseling.getStudentId(), counselingRequest.getStudentId());
        assertEquals(counseling.getContent(), counselingRequest.getContent());
        assertEquals(counseling.getEmployeeId(), 0);
        assertNull(counseling.getFeedback());
        assertFalse(counseling.getIsRead());
    }

    @Test
    @Order(2)
    @DisplayName("Counseling-create-2: 잘못된 학생 아이디")
    void createCounselingWithWrongStudentId() {
        CreateCounselingRequest counselingRequest = new CreateCounselingRequest(0,
                "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        Executable executable = () -> counselingService.createCounseling(counselingRequest);
        assertThrows(EntityNotFoundException.class, executable);
    }

    @Test
    @Order(3)
    @DisplayName("Counseling-feedback-update-1: 상담 피드백 등록 성공")
    void updateFeedback() {
        UpdateFeedbackRequest updateFeedbackRequest = new UpdateFeedbackRequest(1,
                "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        Counseling counseling = counselingService.updateFeedback(updateFeedbackRequest, 1);
        assertEquals(counseling.getEmployeeId(), updateFeedbackRequest.getEmployeeId());
        assertEquals(counseling.getFeedback(), updateFeedbackRequest.getFeedback());
        assertTrue(counseling.getIsRead());
    }

    @Test
    @Order(4)
    @DisplayName("Counseling-feedback-update-2: 존재하지 않는 상담")
    void createCounselingWithWrongCounselingId() {
        UpdateFeedbackRequest updateFeedbackRequest = new UpdateFeedbackRequest(1,
                "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        Executable executable = () -> counselingService.updateFeedback(updateFeedbackRequest, 0);
        assertThrows(EntityNotFoundException.class, executable);
    }

    @Test
    @Order(5)
    @DisplayName("Counseling-feedback-update-3: 이미 처리된 피드백")
    void createCounselingWithAlreadyUpdated() {
        UpdateFeedbackRequest updateFeedbackRequest = new UpdateFeedbackRequest(1,
                "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        Executable executable = () -> counselingService.updateFeedback(updateFeedbackRequest, 1);
        assertThrows(AlreadyUpdatedFeedbackException.class, executable);
    }

    @Test
    @Order(6)
    @DisplayName("Counseling-detail-1: 상담 조회 성공")
    void getCounseling() {
        Counseling counseling = counselingService.getCounseling(1);
        assertEquals(counseling.getId(), 1);
        assertEquals(counseling.getStudentId(), 1);
        assertEquals(counseling.getContent(), "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        assertEquals(counseling.getEmployeeId(), 1);
        assertEquals(counseling.getFeedback(), "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        assertTrue(counseling.getIsRead());
    }

    @Test
    @Order(7)
    @DisplayName("Counseling-detail-2: 상담 조회 성공(담당자 ID 포함)")
    void getCounselingWithEmployeeId() {
        Counseling counseling = counselingService.getCounseling(1, 1);
        assertEquals(counseling.getId(), 1);
        assertEquals(counseling.getStudentId(), 1);
        assertEquals(counseling.getContent(), "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        assertEquals(counseling.getEmployeeId(), 1);
        assertEquals(counseling.getFeedback(), "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        assertTrue(counseling.getIsRead());
    }

    @Test
    @Order(8)
    @DisplayName("Counseling-detail-3: 존재하지 않는 담당자 아이디")
    void getCounselingWithWrongEmployeeId() {
        Executable executable = () -> counselingService.getCounseling(1, 0);
        assertThrows(EntityNotFoundException.class, executable);
    }

    @Test
    @Order(9)
    @DisplayName("Counseling-list-1: 상담 목록 조회 성공(파라미터 없음)")
    void getCounselingList() {
        int defaultPageNo = 0;
        String defaultCriteria = "createdDate";
        String defaultSort = "DESC";
        CounselingSearchRequest counselingSearchRequest = new CounselingSearchRequest(null, null, null, null);
        PageCounselingResponse pageCounselingResponse = counselingService.getCounselingList(defaultPageNo, defaultCriteria, defaultSort, counselingSearchRequest);
        assertEquals(pageCounselingResponse.getContent().get(0).getId(), 1);
        assertEquals(pageCounselingResponse.getContent().get(0).getStudentId(), 1);
        assertEquals(pageCounselingResponse.getContent().get(0).getContent(), "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        assertEquals(pageCounselingResponse.getContent().get(0).getEmployeeId(), 1);
        assertEquals(pageCounselingResponse.getContent().get(0).getFeedback(), "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        assertTrue(pageCounselingResponse.getContent().get(0).getIsRead());
    }
}
