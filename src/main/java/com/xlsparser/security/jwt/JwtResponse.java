package com.xlsparser.security.jwt;

import java.io.Serializable;

public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private final String success;

    public JwtResponse(String jwttoken, String status) {
        this.jwttoken = jwttoken;
        this.success = status;
    }

    public String getToken() {
        return this.jwttoken;
    }

    public String getSuccess() {
        return success;
    }
}
