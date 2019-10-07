package com.assessment.authentication.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "Account Details")
public class AccountDetails implements Serializable {

    public static final long serialVersionUID = -2204464442661579554L;

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
