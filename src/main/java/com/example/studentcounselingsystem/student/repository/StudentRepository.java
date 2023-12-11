package com.example.studentcounselingsystem.student.repository;

import com.example.studentcounselingsystem.student.entity.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface StudentRepository extends CrudRepository<Student, Long> {
    Student findById(int id);
}
