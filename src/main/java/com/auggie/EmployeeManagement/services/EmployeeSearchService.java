package com.auggie.EmployeeManagement.services;

import com.auggie.EmployeeManagement.dto.query.EmployeeQuery;
import com.auggie.EmployeeManagement.entities.Employee;
import com.auggie.EmployeeManagement.mappers.EmployeeMapper;
import com.auggie.EmployeeManagement.repositories.EmployeeSearchRepository;
import lombok.RequiredArgsConstructor;
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


    public Set<EmployeeQuery> search(String search) {
        List<Employee> employeeListByName = employeeSearchRepository.findByNameContainingIgnoreCase(search);
        List<Employee> employeeListByPosition = employeeSearchRepository.findByPositionContainingIgnoreCase(search);

        List<EmployeeQuery> employeesWithDuplicates = Stream.concat(
                employeeListByName
                        .stream()
                        .map(employeeMapper::toEmployeeQuery),
                employeeListByPosition
                        .stream()
                        .map(employeeMapper::toEmployeeQuery)
                ).toList();

        Set<EmployeeQuery> employeeQuerySet = new HashSet<>(employeesWithDuplicates);

        return employeeQuerySet;
    }

}
