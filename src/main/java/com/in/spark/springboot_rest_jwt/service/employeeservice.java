package com.in.spark.springboot_rest_jwt.service;

import com.in.spark.springboot_rest_jwt.model.employee;
import com.in.spark.springboot_rest_jwt.repository.employeerepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class employeeservice implements UserDetailsService {

    @Autowired
    private employeerepository employeerepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        employee emp = employeerepository.findByUsername(username);
        if (emp == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(emp.getUsername(), emp.getPassword(),
                new ArrayList<>());
    }

    public employee saveemployee(employee emp) {
        employee empl = new employee();
        empl.setUsername(emp.getUsername());
        empl.setPassword(bcryptEncoder.encode(emp.getPassword()));
        return employeerepository.save(empl);
    }

}
