package com.assessment.authentication.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "accounts")
@RestController
@RequestMapping("/accounts")
public class OnlineBankingAccount {

    @ApiOperation("Registers a new online banking account")
    @ApiResponses(value = {
            @ApiResponse(code= 201, message = "Account created")
    })

    @PostMapping
    public void registerUserAccount() {

    }
/*
    @PostMapping
    public void authenticateUser() {

    }
*/
    @GetMapping("{id}")
    public String getAccount(String id) {
        return "Authentication API says Hello " + id;
    }
}
