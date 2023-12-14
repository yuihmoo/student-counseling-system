package com.example.studentcounselingsystem.common.exception;

import com.example.studentcounselingsystem.common.dto.response.CustomErrorResponse;
import com.example.studentcounselingsystem.counseling.exception.AlreadyUpdatedFeedbackException;
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
     * @param entityNotFoundException 엔티티가 존재하지 않을 때 발생하는 Exception
     * @return ResponseEntity<CustomErrorResponse>
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<CustomErrorResponse> handleEntityNotFoundException(EntityNotFoundException entityNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomErrorResponse(entityNotFoundException.getMessage()));
    }

    /**
     * DTO validator 를 통해 발생한 Exception 핸들링
     * @param methodArgumentNotValidException DTO Validation 도중 발생하는 Exception
     * @return ResponseEntity<CustomErrorResponse>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<CustomErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new CustomErrorResponse(Objects.requireNonNull(methodArgumentNotValidException.getFieldError()).getDefaultMessage()));
    }

    /**
     * 이미 등록된 피드백으로 발생한 Exception 핸들링
     * @param alreadyUpdatedFeedbackException 이미 등록된 피드백으로 발생한 Exception
     * @return ResponseEntity<CustomErrorResponse>
     */
    @ExceptionHandler(AlreadyUpdatedFeedbackException.class)
    protected ResponseEntity<CustomErrorResponse> handleAlreadyCreatedFeedbackException(AlreadyUpdatedFeedbackException alreadyUpdatedFeedbackException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new CustomErrorResponse(alreadyUpdatedFeedbackException.getMessage()));
    }
}
