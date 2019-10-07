package com.assessment.authentication.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Account Details")
public class AccountDetails {

    @ApiModelProperty(notes = "Account Number")
    private Integer account;

    @ApiModelProperty(notes = "Account IBAN")
    private String iban;

    @ApiModelProperty(notes = "JWT token")
    private String jwttoken;

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getJwttoken() {
        return jwttoken;
    }

    public void setJwttoken(String jwttoken) {
        this.jwttoken = jwttoken;
    }
}
