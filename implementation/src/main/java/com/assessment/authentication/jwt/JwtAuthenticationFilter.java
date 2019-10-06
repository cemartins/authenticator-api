package com.assessment.authentication.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * This class will extend Spring's AuthenticationEntryPoint class and override its method to commence.
 * It rejects every unauthenticated request and sends error code 401
 */
@Component
public class JwtAuthenticationFilter implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -6400194971155854863L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}