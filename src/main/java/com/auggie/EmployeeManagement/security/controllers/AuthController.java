package com.auggie.EmployeeManagement.security.controllers;

import com.auggie.EmployeeManagement.security.EmployeeFiredException;
import com.auggie.EmployeeManagement.security.dto.LoginDTO;
import com.auggie.EmployeeManagement.security.jwt.JwtTokenDTO;
import com.auggie.EmployeeManagement.security.jwt.JwtTokenProvider;
import com.auggie.EmployeeManagement.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/authenticate")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final EmployeeService employeeService;


    @PostMapping("login")
    public ResponseEntity<JwtTokenDTO> login(@RequestBody LoginDTO loginDTO)
            throws BadCredentialsException, EmployeeFiredException
    {

        try{
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(),
                    loginDTO.getPassword()
            );
            Authentication auth = authenticationManager.authenticate(authentication);

            if(employeeService.isEmployeeFired(loginDTO.getUsername()))
                throw new EmployeeFiredException();

            JwtTokenDTO jwtTokenDTO = tokenProvider.generateToken(auth, loginDTO.isRememberMe());
            return new ResponseEntity<>(jwtTokenDTO, HttpStatus.CREATED);
        }
        catch (BadCredentialsException badCredentialsException){
            throw badCredentialsException;
        }
        catch (EmployeeFiredException employeeFiredException){
            throw employeeFiredException;
        }
        catch (Exception e){
            log.error("Login error. Message: {}", e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
