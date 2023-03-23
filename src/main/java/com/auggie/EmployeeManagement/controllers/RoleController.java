package com.auggie.EmployeeManagement.controllers;

import com.auggie.EmployeeManagement.dto.command.RoleCreateCommand;
import com.auggie.EmployeeManagement.dto.query.RoleQuery;
import com.auggie.EmployeeManagement.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<Set<RoleQuery>> findAll(){
        Set<RoleQuery> roles = roleService.findAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createRole(@RequestBody RoleCreateCommand roleCreateCommand){
        roleService.createRole(roleCreateCommand);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable("id") Integer id){
        roleService.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
