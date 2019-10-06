package com.assessment.authentication.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@ApiModel(description = "Account Details.")
public class AccountRegister implements Serializable {

    private static final long serialVersionUID = 6546936552005481434L;

    @ApiModelProperty(notes = "User Name")
    private String username;

    @ApiModelProperty(notes = "Password Name")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @ApiModelProperty(notes = "Account Number")
    private Integer account;

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

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }
}
