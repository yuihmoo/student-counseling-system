package com.example.studentcounselingsystem.counseling.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@Schema(description = "상담 등록 DTO")
public class CreateCounselingRequest {
    @Schema(description = "학생 아이디", example = "1")
    @NotEmpty()
    private int studentId;
    @Schema(description = "상담 내용", example = "string", minLength = 50, maxLength = 1000)
    @NotEmpty()
    @Length(min = 50, max = 1000, message = "상담 내용은 최소 50자 이상 최대 1000자까지 입니다.")
    private String content;
}
