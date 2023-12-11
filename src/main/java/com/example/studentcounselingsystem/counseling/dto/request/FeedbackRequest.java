package com.example.studentcounselingsystem.counseling.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Getter
public class FeedbackRequest {
    private UUID id;
    private UUID counselorId;
    @NotEmpty()
    @Length(min = 50, max = 1000, message = "상담 내용은 최소 50자 이상 최대 1000자까지 입니다.")
    private String feedback;
}
