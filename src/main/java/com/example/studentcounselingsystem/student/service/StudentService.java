package com.example.studentcounselingsystem.student.service;

import com.example.studentcounselingsystem.student.entity.Student;
import com.example.studentcounselingsystem.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentService {
    private final StudentRepository studentRepository;

    /**
     * 학생 ID로 학생 존재 여부 판단
     * @param studentId: 학생 ID
     * @return boolean
     */
    @Transactional
    public Student getStudentById(int studentId) {
        return studentRepository.findById(studentId);
    }
}
