package com.auggie.EmployeeManagement.security;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AuthExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialException(BadCredentialsException badCredentialsException){
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Bad credentials!!!");
        response.put("code", "bad-credentials");

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
