package com.example.studentcounselingsystem.common.exception;

import com.example.studentcounselingsystem.common.dto.response.CustomErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 리소스 존재 여부 확인 시 발생하는 Exception 핸들링
     * @param entityNotFoundException
     * @return
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<CustomErrorResponse> handleEntityNotFoundException(EntityNotFoundException entityNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomErrorResponse(entityNotFoundException.getMessage()));
    }
}
