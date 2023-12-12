package com.example.studentcounselingsystem.counseling.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CounselingSearchRequest {
    private Integer studentId;
    private Integer counselorId;
    private Boolean isRead;
    private Boolean isFeedback;
}
