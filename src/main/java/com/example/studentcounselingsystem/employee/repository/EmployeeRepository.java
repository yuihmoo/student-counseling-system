package com.example.studentcounselingsystem.employee.repository;

import com.example.studentcounselingsystem.employee.entity.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
//    @Query(value = "SELECT id, name, type, status FROM employee WHERE id = UUID_TO_BIN(:id, 1)", nativeQuery = true)
    Employee findById(int id);
}
