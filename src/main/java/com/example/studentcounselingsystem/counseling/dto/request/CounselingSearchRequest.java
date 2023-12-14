package com.example.studentcounselingsystem.counseling.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CounselingSearchRequest {
    @Schema(description = "학생 아이디", example = "1")
    private Integer studentId;
    @Schema(description = "담당자 아이디", example = "1")
    private Integer employeeId;
    @Schema(description = "상담 읽음 여부", example = "true")
    private Boolean isRead;
    @Schema(description = "상담 피드백 등록 여부", example = "true")
    private Boolean isFeedback;
}
