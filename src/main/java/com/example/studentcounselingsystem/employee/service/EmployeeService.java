package com.example.studentcounselingsystem.employee.service;

import com.example.studentcounselingsystem.employee.entity.Employee;
import com.example.studentcounselingsystem.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public Employee findById(int id) {
        return employeeRepository.findById(id);
    }
}
