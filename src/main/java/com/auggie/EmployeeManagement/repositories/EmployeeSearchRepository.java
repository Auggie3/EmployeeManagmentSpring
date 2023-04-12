package com.auggie.EmployeeManagement.repositories;

import com.auggie.EmployeeManagement.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeSearchRepository extends JpaRepository<Employee, Integer> {
    
    List<Employee> findByNameContainingIgnoreCase(String search);
    List<Employee> findByPositionContainingIgnoreCase(String search);
    List<Employee> findByNameContainingIgnoreCaseOrPositionContainingIgnoreCase(String name, String position);
    Page<Employee> findByNameContainingIgnoreCaseOrPositionContainingIgnoreCase(String name,
                                                                                        String position,
                                                                                        Pageable pageable);
}
