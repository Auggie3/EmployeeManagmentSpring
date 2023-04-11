package com.auggie.EmployeeManagement.security.components;

import com.auggie.EmployeeManagement.entities.Employee;
import com.auggie.EmployeeManagement.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmployeeService employeeService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> optionalEmployee = employeeService.findByUsernameWithRoles(username);
        if(optionalEmployee.isPresent()){
            Employee employee = optionalEmployee.get();

            List<GrantedAuthority> authorityList = employee.getRoles()
                    .stream()
                    .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role.getName()))
                    .toList();


            return new User(
                employee.getUsername(), employee.getPassword(), authorityList
            );

        }else {
            throw new UsernameNotFoundException("User with username " + username + "not found!");
        }
    }
}
