package com.auggie.EmployeeManagement.controllers;

import com.auggie.EmployeeManagement.dto.query.EmployeeQuery;
import com.auggie.EmployeeManagement.entities.Employee;
import com.auggie.EmployeeManagement.services.EmployeeSearchService;
import com.auggie.EmployeeManagement.services.EmployeeService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employee")
public class EmployeeSearchController {

    private final EmployeeSearchService employeeSearchService;


    @GetMapping("search")
    public ResponseEntity<Set<EmployeeQuery>> search(@RequestParam("search") String search){
        Set<EmployeeQuery> employeeQuerySet = employeeSearchService.search(search);
        return new ResponseEntity<>(employeeQuerySet, HttpStatus.OK);
    }
}
