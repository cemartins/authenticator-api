package com.assessment.authentication.api;

import com.assessment.authentication.api.dto.AuthenticationRequest;
import com.assessment.authentication.jwt.JwtTokenProcessor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Controller used to authenticate an API user.
 */
@Api(value="Authentication")
@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProcessor jwtTokenProcessor;

    @Autowired
    private UserDetailsService jwtUserDetailsService;


    /**
     * Tried to authenticate a previously registered API user.
     * @param authenticationRequest
     * @return Returns the user's JWT token if successful
     * @throws Exception
     */
    @ApiOperation("Autheticate a user")
    @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createAuthenticationToken(@ApiParam(value = "Employee object store in database table", required = true) @Valid @RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        final Authentication authentication = authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenProcessor.generateToken(userDetails);
        return ResponseEntity.ok(token);
    }

    private Authentication authenticate(String username, String password) throws Exception {
        try {
            final Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return authenticate;
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }}
