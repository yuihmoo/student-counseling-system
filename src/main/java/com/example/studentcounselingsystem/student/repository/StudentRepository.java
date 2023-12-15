package com.example.studentcounselingsystem.student.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.studentcounselingsystem.student.entity.Student;

public interface StudentRepository extends CrudRepository<Student, Long> {
    Student findById(int id);
}
