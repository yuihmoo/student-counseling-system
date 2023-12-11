package com.example.studentcounselingsystem.student.service;

import com.example.studentcounselingsystem.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public boolean isExistStudentById(UUID studentId) {
        return studentRepository.existsById(studentId);
    }
}
