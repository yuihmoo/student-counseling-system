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

    @Test
    @Order(10)
    @DisplayName("Counseling-list-2: 상담 목록 조회 성공(오름차순)")
    void getCounselingListWithASC() {
        int defaultPageNo = 0;
        String defaultCriteria = "createdDate";
        String defaultSort = "ASC";
        CounselingSearchRequest counselingSearchRequest = new CounselingSearchRequest(null, null, null, null);
        PageCounselingResponse pageCounselingResponse = counselingService.getCounselingList(defaultPageNo, defaultCriteria, defaultSort, counselingSearchRequest);
        assertEquals(pageCounselingResponse.getContent().get(0).getId(), 1);
        assertEquals(pageCounselingResponse.getContent().get(0).getStudentId(), 1);
        assertEquals(pageCounselingResponse.getContent().get(0).getContent(), "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        assertEquals(pageCounselingResponse.getContent().get(0).getEmployeeId(), 1);
        assertEquals(pageCounselingResponse.getContent().get(0).getFeedback(), "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        assertTrue(pageCounselingResponse.getContent().get(0).getIsRead());
    }

    @Test
    @Order(11)
    @DisplayName("Counseling-list-3: 상담 목록 조회 성공(학생 아이디)")
    void getCounselingListWithStudentId() {
        int defaultPageNo = 0;
        String defaultCriteria = "createdDate";
        String defaultSort = "DESC";
        CounselingSearchRequest counselingSearchRequest = new CounselingSearchRequest(1, null, null, null);
        PageCounselingResponse pageCounselingResponse = counselingService.getCounselingList(defaultPageNo, defaultCriteria, defaultSort, counselingSearchRequest);
        assertEquals(pageCounselingResponse.getContent().get(0).getId(), 1);
        assertEquals(pageCounselingResponse.getContent().get(0).getStudentId(), 1);
        assertEquals(pageCounselingResponse.getContent().get(0).getContent(), "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        assertEquals(pageCounselingResponse.getContent().get(0).getEmployeeId(), 1);
        assertEquals(pageCounselingResponse.getContent().get(0).getFeedback(), "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        assertTrue(pageCounselingResponse.getContent().get(0).getIsRead());
    }

    @Test
    @Order(12)
    @DisplayName("Counseling-list-4: 상담 목록 조회 성공(담당자 아이디)")
    void getCounselingListWithEmployeeId() {
        int defaultPageNo = 0;
        String defaultCriteria = "createdDate";
        String defaultSort = "DESC";
        CounselingSearchRequest counselingSearchRequest = new CounselingSearchRequest(null, 1, null, null);
        PageCounselingResponse pageCounselingResponse = counselingService.getCounselingList(defaultPageNo, defaultCriteria, defaultSort, counselingSearchRequest);
        assertEquals(pageCounselingResponse.getContent().get(0).getId(), 1);
        assertEquals(pageCounselingResponse.getContent().get(0).getStudentId(), 1);
        assertEquals(pageCounselingResponse.getContent().get(0).getContent(), "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        assertEquals(pageCounselingResponse.getContent().get(0).getEmployeeId(), 1);
        assertEquals(pageCounselingResponse.getContent().get(0).getFeedback(), "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        assertTrue(pageCounselingResponse.getContent().get(0).getIsRead());
    }

    @Test
    @Order(13)
    @DisplayName("Counseling-list-5: 상담 목록 조회 성공(읽음 여부 true)")
    void getCounselingListWithIsReadTrue() {
        int defaultPageNo = 0;
        String defaultCriteria = "createdDate";
        String defaultSort = "DESC";
        CounselingSearchRequest counselingSearchRequest = new CounselingSearchRequest(null, null, true, null);
        PageCounselingResponse pageCounselingResponse = counselingService.getCounselingList(defaultPageNo, defaultCriteria, defaultSort, counselingSearchRequest);
        assertEquals(pageCounselingResponse.getContent().get(0).getId(), 1);
        assertEquals(pageCounselingResponse.getContent().get(0).getStudentId(), 1);
        assertEquals(pageCounselingResponse.getContent().get(0).getContent(), "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        assertEquals(pageCounselingResponse.getContent().get(0).getEmployeeId(), 1);
        assertEquals(pageCounselingResponse.getContent().get(0).getFeedback(), "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        assertTrue(pageCounselingResponse.getContent().get(0).getIsRead());
    }

    @Test
    @Order(14)
    @DisplayName("Counseling-list-6: 상담 목록 조회 성공(읽음 여부 false)")
    void getCounselingListWithIsReadFalse() {
        int defaultPageNo = 0;
        String defaultCriteria = "createdDate";
        String defaultSort = "DESC";
        CounselingSearchRequest counselingSearchRequest = new CounselingSearchRequest(null, null, false, null);
        PageCounselingResponse pageCounselingResponse = counselingService.getCounselingList(defaultPageNo, defaultCriteria, defaultSort, counselingSearchRequest);
        assertEquals(pageCounselingResponse.getTotalElements(), 0);
    }

    @Test
    @Order(15)
    @DisplayName("Counseling-list-7: 상담 목록 조회 성공(피드백 여부 true)")
    void getCounselingListWithIsFeedbackTrue() {
        int defaultPageNo = 0;
        String defaultCriteria = "createdDate";
        String defaultSort = "DESC";
        CounselingSearchRequest counselingSearchRequest = new CounselingSearchRequest(null, null, null, true);
        PageCounselingResponse pageCounselingResponse = counselingService.getCounselingList(defaultPageNo, defaultCriteria, defaultSort, counselingSearchRequest);
        assertEquals(pageCounselingResponse.getContent().get(0).getId(), 1);
        assertEquals(pageCounselingResponse.getContent().get(0).getStudentId(), 1);
        assertEquals(pageCounselingResponse.getContent().get(0).getContent(), "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        assertEquals(pageCounselingResponse.getContent().get(0).getEmployeeId(), 1);
        assertEquals(pageCounselingResponse.getContent().get(0).getFeedback(), "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        assertTrue(pageCounselingResponse.getContent().get(0).getIsRead());
    }

    @Test
    @Order(16)
    @DisplayName("Counseling-list-8: 상담 목록 조회 성공(피드백 여부 false)")
    void getCounselingListWithIsFeedbackFalse() {
        int defaultPageNo = 0;
        String defaultCriteria = "createdDate";
        String defaultSort = "DESC";
        CounselingSearchRequest counselingSearchRequest = new CounselingSearchRequest(null, null, null, false);
        PageCounselingResponse pageCounselingResponse = counselingService.getCounselingList(defaultPageNo, defaultCriteria, defaultSort, counselingSearchRequest);
        assertEquals(pageCounselingResponse.getContent().size(), 0);
    }

    @Test
    @Order(17)
    @DisplayName("Counseling-list-9: 상담 목록 조회 성공(학생 아이디, 담당자 아이디)")
    void getCounselingListWithStudentIdAndEmployeeId() {
        int defaultPageNo = 0;
        String defaultCriteria = "createdDate";
        String defaultSort = "DESC";
        CounselingSearchRequest counselingSearchRequest = new CounselingSearchRequest(1, 1, null, null);
        PageCounselingResponse pageCounselingResponse = counselingService.getCounselingList(defaultPageNo, defaultCriteria, defaultSort, counselingSearchRequest);
        assertEquals(pageCounselingResponse.getContent().get(0).getId(), 1);
        assertEquals(pageCounselingResponse.getContent().get(0).getStudentId(), 1);
        assertEquals(pageCounselingResponse.getContent().get(0).getContent(), "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        assertEquals(pageCounselingResponse.getContent().get(0).getEmployeeId(), 1);
        assertEquals(pageCounselingResponse.getContent().get(0).getFeedback(), "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        assertTrue(pageCounselingResponse.getContent().get(0).getIsRead());
    }

    @Test
    @Order(17)
    @DisplayName("Counseling-list-10: 상담 목록 조회 성공(학생 아이디, 읽음 여부 true)")
    void getCounselingListWithStudentIdAndIsReadTrue() {
        int defaultPageNo = 0;
        String defaultCriteria = "createdDate";
        String defaultSort = "DESC";
        CounselingSearchRequest counselingSearchRequest = new CounselingSearchRequest(1, null, true, null);
        PageCounselingResponse pageCounselingResponse = counselingService.getCounselingList(defaultPageNo, defaultCriteria, defaultSort, counselingSearchRequest);
        assertEquals(pageCounselingResponse.getContent().get(0).getId(), 1);
        assertEquals(pageCounselingResponse.getContent().get(0).getStudentId(), 1);
        assertEquals(pageCounselingResponse.getContent().get(0).getContent(), "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        assertEquals(pageCounselingResponse.getContent().get(0).getEmployeeId(), 1);
        assertEquals(pageCounselingResponse.getContent().get(0).getFeedback(), "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        assertTrue(pageCounselingResponse.getContent().get(0).getIsRead());
    }

    @Test
    @Order(18)
    @DisplayName("Counseling-list-11: 상담 목록 조회 성공(학생 아이디, 피드백 여부 true)")
    void getCounselingListWithStudentIdAndIsFeedbackTrue() {
        int defaultPageNo = 0;
        String defaultCriteria = "createdDate";
        String defaultSort = "DESC";
        CounselingSearchRequest counselingSearchRequest = new CounselingSearchRequest(1, null, null, true);
        PageCounselingResponse pageCounselingResponse = counselingService.getCounselingList(defaultPageNo, defaultCriteria, defaultSort, counselingSearchRequest);
        assertEquals(pageCounselingResponse.getContent().get(0).getId(), 1);
        assertEquals(pageCounselingResponse.getContent().get(0).getStudentId(), 1);
        assertEquals(pageCounselingResponse.getContent().get(0).getContent(), "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        assertEquals(pageCounselingResponse.getContent().get(0).getEmployeeId(), 1);
        assertEquals(pageCounselingResponse.getContent().get(0).getFeedback(), "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit");
        assertTrue(pageCounselingResponse.getContent().get(0).getIsRead());
    }
}
