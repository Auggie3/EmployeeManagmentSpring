package com.auggie.EmployeeManagement.services;

import com.auggie.EmployeeManagement.dto.query.EmployeeQuery;
import com.auggie.EmployeeManagement.entities.Employee;
import com.auggie.EmployeeManagement.mappers.EmployeeMapper;
import com.auggie.EmployeeManagement.repositories.EmployeeSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class EmployeeSearchService {
    private final EmployeeSearchRepository employeeSearchRepository;
    private final EmployeeMapper employeeMapper;


    public List<EmployeeQuery> search(String search) {

        List<Employee> employeesSearch = employeeSearchRepository
                .findByNameContainingIgnoreCaseOrPositionContainingIgnoreCase(search, search);

        return employeesSearch
                .stream()
                .map(employeeMapper::toEmployeeQuery)
                .toList();
    }

    public Page<EmployeeQuery> searchPage(String search, Pageable pageable) {
        Page<Employee> employeePage = employeeSearchRepository.
                findByNameContainingIgnoreCaseOrPositionContainingIgnoreCase(search,search,pageable);

        List<EmployeeQuery> employeeQueryList = employeePage.getContent()
                .stream()
                .map(employeeMapper::toEmployeeQuery)
                .toList();

        return new PageImpl<>(employeeQueryList, pageable, employeePage.getTotalElements());
    }

    public List<EmployeeQuery> filter(Specification<Employee> employeeSpecification) {
        List<Employee> employees = employeeSearchRepository.findAll(employeeSpecification);
        return employees
                .stream()
                .map(employeeMapper::toEmployeeQuery)
                .toList();
    }
}
