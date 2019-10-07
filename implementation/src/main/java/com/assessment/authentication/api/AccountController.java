package com.assessment.authentication.api;

import com.assessment.authentication.api.dto.AccountDetails;
import com.assessment.authentication.jwt.JwtUserDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Account Management")
@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @ApiOperation("Get a specific banking account")
    @GetMapping("/{id}")
    @Secured("ROLE_USER")
    public ResponseEntity<AccountDetails> getAccount(Integer id) {

        final User user = userDetailsService.loadUserByAccountNumber(id);
        AccountDetails ar = new AccountDetails();
        ar.setAccount(id);
        return ResponseEntity.ok(ar);
    }

}
