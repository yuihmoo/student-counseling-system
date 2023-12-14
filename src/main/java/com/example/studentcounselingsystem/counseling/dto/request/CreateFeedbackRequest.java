package com.example.studentcounselingsystem.counseling.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class CreateFeedbackRequest {
    @Schema(description = "담당자 아이디", example = "1")
    @Min(value = 1, message = "담당자 아이디 값을 다시 확인해주세요.")
    private int employeeId;
    @Schema(description = "피드백 내용", example = "string", minLength = 50, maxLength = 1000)
    @NotEmpty()
    @Length(min = 50, max = 1000, message = "상담 내용은 최소 50자 이상 최대 1000자까지 입니다.")
    private String feedback;
}
