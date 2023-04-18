package com.auggie.EmployeeManagement.controllers;

import com.auggie.EmployeeManagement.dto.query.EmployeeQuery;
import com.auggie.EmployeeManagement.entities.Employee;
import com.auggie.EmployeeManagement.filters.EmployeeFilter;
import com.auggie.EmployeeManagement.filters.EmployeeSpecification;
import com.auggie.EmployeeManagement.services.EmployeeSearchService;
import com.auggie.EmployeeManagement.services.EmployeeService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employee")
public class EmployeeSearchController {

    private final EmployeeSearchService employeeSearchService;


    @GetMapping("search")
    public ResponseEntity<List<EmployeeQuery>> search(@RequestParam("search") String search){
        List<EmployeeQuery> employeeQueryList = employeeSearchService.search(search);
        return new ResponseEntity<>(employeeQueryList, HttpStatus.OK);
    }

    @GetMapping("search/page")
    public ResponseEntity<Page<EmployeeQuery>> searchPage(@RequestParam("search") String search, Pageable pageable){
        Page<EmployeeQuery> employeeQueryPage = employeeSearchService.searchPage(search, pageable);
        return new ResponseEntity<>(employeeQueryPage, HttpStatus.OK);
    }

    @GetMapping("filter")
    public ResponseEntity<List<EmployeeQuery>> filter(@RequestBody EmployeeFilter employeeFilter){
        log.info("spec:  {}", employeeFilter);

        Specification<Employee> employeeSpecification = new EmployeeSpecification(employeeFilter);
        List<EmployeeQuery> employeeQueryList = employeeSearchService.filter(employeeSpecification);
        return new ResponseEntity<>(employeeQueryList, HttpStatus.OK);
    }
}
