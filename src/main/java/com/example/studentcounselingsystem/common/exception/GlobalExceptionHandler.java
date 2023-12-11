package com.example.studentcounselingsystem.common.exception;

import com.example.studentcounselingsystem.common.dto.response.CustomErrorResponse;
import com.example.studentcounselingsystem.counseling.exception.AlreadyCreatedFeedbackException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

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

    /**
     * DTO validator 를 통해 발생한 Exception 핸들링
     * @param methodArgumentNotValidException
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<CustomErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new CustomErrorResponse(Objects.requireNonNull(methodArgumentNotValidException.getFieldError()).getDefaultMessage()));
    }

    @ExceptionHandler(AlreadyCreatedFeedbackException.class)
    protected ResponseEntity<CustomErrorResponse> handleAlreadyCreatedFeedbackException(AlreadyCreatedFeedbackException alreadyCreatedFeedbackException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new CustomErrorResponse(alreadyCreatedFeedbackException.getMessage()));
    }
}
