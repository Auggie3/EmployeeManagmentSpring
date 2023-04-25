package com.auggie.EmployeeManagement.security.components;

import com.auggie.EmployeeManagement.dto.query.VacationQuery;
import com.auggie.EmployeeManagement.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class CustomAuthComponent {

    public boolean hasPermision(Authentication authentication,Integer id){

        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
            return true;


        return false;
    }

}
