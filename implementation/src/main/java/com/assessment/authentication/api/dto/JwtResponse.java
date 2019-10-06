package com.assessment.authentication.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "Authentication Result.")
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -2617174486963474395L;

    @ApiModelProperty(notes = "JWT token")
    private final String jwttoken;

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }
}
