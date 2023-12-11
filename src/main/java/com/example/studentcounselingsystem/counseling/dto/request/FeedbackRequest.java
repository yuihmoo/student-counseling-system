package com.example.studentcounselingsystem.counseling.dto.request;

import lombok.Getter;

import java.util.UUID;

@Getter
public class FeedbackRequest {
    private UUID id;
    private UUID counselorId;
    private String feedback;
}
