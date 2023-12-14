package com.example.studentcounselingsystem.employee.service;

import com.example.studentcounselingsystem.employee.entity.Employee;
import com.example.studentcounselingsystem.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Transactional
    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id);
    }
}
