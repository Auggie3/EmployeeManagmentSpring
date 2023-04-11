package com.auggie.EmployeeManagement.controllers;


import com.auggie.EmployeeManagement.dto.ChangePasswordDTO;
import com.auggie.EmployeeManagement.dto.EmployeeRoleDTO;
import com.auggie.EmployeeManagement.dto.command.EmployeeCreateCommand;
import com.auggie.EmployeeManagement.dto.command.EmployeeUpdateCommand;
import com.auggie.EmployeeManagement.dto.query.*;
import com.auggie.EmployeeManagement.errorsAndValidation.ValidationActivator;
import com.auggie.EmployeeManagement.errorsAndValidation.ValidationException;
import com.auggie.EmployeeManagement.services.EmployeeService;
import jakarta.persistence.Version;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ValidationActivator validationActivator;


    @GetMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<EmployeeQuery>> findAll(){
        List<EmployeeQuery> allEmployees = employeeService.findAll();
        return new ResponseEntity<>(allEmployees, HttpStatus.OK);
    }

    @GetMapping("details")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<EmployeeDetailsQuery>> findAllWithDetails(){
        List<EmployeeDetailsQuery> allEmployees = employeeService.findAllWithDetails();
        return new ResponseEntity<>(allEmployees,HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("@customAuthComponent.hasPermision(authentication, #id)")
    public ResponseEntity<EmployeeQuery> findById(@PathVariable("id") Integer id){
        EmployeeQuery employeeQuery = employeeService.findById(id);
        return new ResponseEntity<>(employeeQuery,HttpStatus.OK);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("@customAuthComponent.hasPermision(authentication, #id)")
    public ResponseEntity<EmployeeDetailsQuery> findByIdWithDetails(@PathVariable("id") Integer id){
        EmployeeDetailsQuery employeeDetailsQuery = employeeService.findByIdWithDetails(id);
        return new ResponseEntity<>(employeeDetailsQuery,HttpStatus.OK);
    }

    @GetMapping("/details/me")
    public ResponseEntity<EmployeeDetailsQuery> findByJWTCredentials(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer id = (Integer)authentication.getCredentials();
        EmployeeDetailsQuery employeeDetailsQuery = employeeService.findByIdWithDetails(id);
        return new ResponseEntity<>(employeeDetailsQuery,HttpStatus.OK);
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<EmployeeQuery> createEmployee(@RequestBody @Valid EmployeeCreateCommand employeeCreateCommand)
    throws ValidationException
    {
        validationActivator.activateUsernameValidator(employeeCreateCommand);
        EmployeeQuery employeeQuery = employeeService.createEmployee(employeeCreateCommand);
        return new ResponseEntity<>(employeeQuery, HttpStatus.CREATED);
    }

    @PutMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> updateEmployee(@RequestBody EmployeeUpdateCommand employeeUpdateCommand)
    throws ValidationException
    {
        validationActivator.activateUsernameValidator(employeeUpdateCommand);
        employeeService.updateEmployee(employeeUpdateCommand);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Integer id){
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/past")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> addPastEmployment(@RequestBody PastEmploymentQuery pastEmploymentQuery) throws ValidationException{
        validationActivator.activatePastEmploymentValidator(pastEmploymentQuery);
        employeeService.addPastEmployment(pastEmploymentQuery);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/past")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> deletePastEmployment(@RequestBody PastEmploymentQuery pastEmploymentQuery){
        employeeService.deletePastEmployment(pastEmploymentQuery);
        return new ResponseEntity<>(HttpStatus.OK);
    }




    @GetMapping("role/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<RoleQuery>> findRoles(@PathVariable("id") Integer id){
        List<RoleQuery> employeeRoles = employeeService.findRoles(id);
        return new ResponseEntity<>(employeeRoles,HttpStatus.OK);
    }

    @PutMapping("/role")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> addRoleToEmployee(@RequestBody EmployeeRoleDTO employeeRoleDTO){

        employeeService.addRole(employeeRoleDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/role")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> removeRoleFromEmployee(@RequestBody EmployeeRoleDTO employeeRoleDTO){
        employeeService.removeRole(employeeRoleDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid ChangePasswordDTO changePasswordDTO) throws ValidationException{
        Integer id = (Integer)SecurityContextHolder.getContext().getAuthentication().getCredentials();
        validationActivator.activateChangePasswordValidator(changePasswordDTO);
        employeeService.changePassword(changePasswordDTO.getNewPassword(), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Version
    private int version;


}
