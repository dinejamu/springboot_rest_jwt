package com.in.spark.springboot_rest_jwt.repository;

import com.in.spark.springboot_rest_jwt.model.employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface employeerepository extends JpaRepository<employee, Integer>{
    employee findByUsername(String username);
}
