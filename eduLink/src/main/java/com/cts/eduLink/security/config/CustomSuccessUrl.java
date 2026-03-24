package com.cts.eduLink.security.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessUrl implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String role = authentication.getAuthorities()
                .iterator()
                .next()
                .getAuthority();

        switch (role) {
            case "ROLE_STUDENT" -> response.sendRedirect("/studentHome");
            case "ROLE_FACULTY" -> response.sendRedirect("/facultyHome");
            case "ROLE_ADMIN" -> response.sendRedirect("/adminHomeUi");
            default -> response.sendRedirect("/login");
        }
    }
}
