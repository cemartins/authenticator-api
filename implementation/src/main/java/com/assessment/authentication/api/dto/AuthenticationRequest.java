package com.assessment.authentication.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "Login Creadentials.")
public class AuthenticationRequest implements Serializable {

    private static final long serialVersionUID = 2397211209827369265L;

    @ApiModelProperty(notes = "User Name")
    private String username;

    @ApiModelProperty(notes = "Password Name")
    private String password;

    public AuthenticationRequest() {

    }

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
