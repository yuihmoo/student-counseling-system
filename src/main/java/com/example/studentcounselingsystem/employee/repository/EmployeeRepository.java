package com.example.studentcounselingsystem.employee.repository;

import com.example.studentcounselingsystem.employee.entity.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Employee findById(UUID id);
}
