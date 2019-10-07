package com.assessment.authentication.api;

import com.assessment.authentication.api.dto.AccountRegister;
import com.assessment.authentication.jwt.JwtUserDetailsService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "Account Registration")
@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @ApiOperation("Registers a new online banking account")
    @ApiResponses(value = {
            @ApiResponse(code= 200, message = "Account created")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registerUserAccount(@ApiParam(value = "Employee object store in database table", required = true) @Valid @RequestBody AccountRegister accountRegister) {

        userDetailsService.storeUser(accountRegister.getAccount(), accountRegister.getUsername(), accountRegister.getPassword());
        return ResponseEntity.ok().build();
    }
}
