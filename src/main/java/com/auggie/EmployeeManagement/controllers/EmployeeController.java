package com.auggie.EmployeeManagement.controllers;


import com.auggie.EmployeeManagement.dto.command.EmployeeCreateCommand;
import com.auggie.EmployeeManagement.dto.command.EmployeeUpdateCommand;
import com.auggie.EmployeeManagement.dto.query.EmployeeDetailsQuery;
import com.auggie.EmployeeManagement.dto.query.EmployeeQuery;
import com.auggie.EmployeeManagement.dto.query.PastEmploymentQuery;
import com.auggie.EmployeeManagement.dto.query.VacationQuery;
import com.auggie.EmployeeManagement.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeQuery>> findAll(){
        List<EmployeeQuery> allEmployees = employeeService.findAll();
        return new ResponseEntity<>(allEmployees, HttpStatus.OK);
    }

    @GetMapping("details")
    public ResponseEntity<List<EmployeeDetailsQuery>> findAllWithDetails(){
        List<EmployeeDetailsQuery> allEmployees = employeeService.findAllWithDetails();
        return new ResponseEntity<>(allEmployees,HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<EmployeeQuery> findById(@PathVariable("id") Integer id){
        EmployeeQuery employeeQuery = employeeService.findById(id);
        return new ResponseEntity<>(employeeQuery,HttpStatus.OK);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<EmployeeDetailsQuery> findByIdWithDetails(@PathVariable("id") Integer id){
        EmployeeDetailsQuery employeeDetailsQuery = employeeService.findByIdWithDetails(id);
        return new ResponseEntity<>(employeeDetailsQuery,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EmployeeQuery> createEmployee(@RequestBody EmployeeCreateCommand employeeCreateCommand){
        EmployeeQuery employeeQuery = employeeService.createEmployee(employeeCreateCommand);
        return new ResponseEntity<>(employeeQuery, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> updateEmployee(@RequestBody EmployeeUpdateCommand employeeUpdateCommand){
        employeeService.updateEmployee(employeeUpdateCommand);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Integer id){
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/past")
    public ResponseEntity<Void> addPastEmployment(@RequestBody PastEmploymentQuery pastEmploymentQuery){
        employeeService.addPastEmployment(pastEmploymentQuery);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/past")
    public ResponseEntity<Void> deletePastEmployment(@RequestBody PastEmploymentQuery pastEmploymentQuery){
        employeeService.deletePastEmployment(pastEmploymentQuery);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/vacation")
    public ResponseEntity<Void> addVacation(@RequestBody VacationQuery vacationQuery){
        employeeService.addVacation(vacationQuery);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/vacation")
    public ResponseEntity<Void> deleteVacation(@RequestBody VacationQuery vacationQuery){
        employeeService.deleteVacation(vacationQuery);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
