package com.auggie.EmployeeManagement.controllers;

import com.auggie.EmployeeManagement.dto.command.RoleCreateCommand;
import com.auggie.EmployeeManagement.dto.query.RoleQuery;
import com.auggie.EmployeeManagement.errorsAndValidation.ValidationActivator;
import com.auggie.EmployeeManagement.errorsAndValidation.ValidationException;
import com.auggie.EmployeeManagement.services.RoleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Log4j2
@Secured("ROLE_ADMIN")
@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ValidationActivator validationActivator;

    @GetMapping
    public ResponseEntity<Set<RoleQuery>> findAll(){
        Set<RoleQuery> roles = roleService.findAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createRole(@RequestBody RoleCreateCommand roleCreateCommand) throws ValidationException {
        RoleCreateCommand roleCreateCommandUpperCase = new RoleCreateCommand();
        roleCreateCommandUpperCase.setName(roleCreateCommand.getName().toUpperCase());

        roleCreateCommandUpperCase.setDescription(roleCreateCommand.getDescription());

        validationActivator.activateRoleValidator(roleCreateCommandUpperCase);
        roleService.createRole(roleCreateCommandUpperCase);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable("id") Integer id){
        //ROLE_ADMIN SHOULDNT BE DELETED
        if(id!=1)
            roleService.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
