package com.in.spark.springboot_rest_jwt.model;

import java.io.Serializable;

public class authresponese implements Serializable {

    private final String jwt;

    public authresponese(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
