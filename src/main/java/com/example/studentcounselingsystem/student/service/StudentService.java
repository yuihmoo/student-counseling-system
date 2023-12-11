package com.example.studentcounselingsystem.student.service;

import com.example.studentcounselingsystem.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    /**
     * 학생 ID로 학생 존재 여부 판단
     * @param studentId: 학생 ID
     * @return boolean
     */
    public boolean isExistStudentById(UUID studentId) {
        return studentRepository.existsById(studentId);
    }
}
