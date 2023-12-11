package com.example.studentcounselingsystem.counseling.dto.request;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CounselingRequest {
    private UUID studentId;
    private String content;
}
