package com.in.spark.springboot_rest_jwt.controller;

import com.in.spark.springboot_rest_jwt.model.authrequest;
import com.in.spark.springboot_rest_jwt.model.authresponese;
import com.in.spark.springboot_rest_jwt.model.employee;
import com.in.spark.springboot_rest_jwt.repository.employeerepository;
import com.in.spark.springboot_rest_jwt.service.employeeservice;
import com.in.spark.springboot_rest_jwt.util.jwtutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class homecontroller {

    @Autowired
    private employeeservice employeeser;

    @Autowired
    private jwtutil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private employeerepository employeerepo;

    @RequestMapping({"/"})
    public String firstPage() {
        return "welcome to sparks Application";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> savedetails(@RequestBody employee emp) {
        employee employ = employeerepo.findByUsername(emp.getUsername());
        if (employ == null) {
            employ = employeeser.saveemployee(emp);
        } else {
            return ResponseEntity.ok("User Name is exits");
        }
        return ResponseEntity.ok(employ);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody authrequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = employeeser
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt_Token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new authresponese(jwt_Token));
    }


}
