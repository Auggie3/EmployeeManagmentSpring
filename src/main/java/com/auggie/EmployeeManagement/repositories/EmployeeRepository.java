package com.auggie.EmployeeManagement.repositories;

import com.auggie.EmployeeManagement.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    @Query(value = """
        Select employee from Employee employee
        left join employee.vacations
        left join employee.pastEmployments
        left join employee.roles
    """)
    List<Employee> findAllWithDetails();

    @Query(value =
    """
        select  employee from Employee  employee
        left join employee.vacations
        left join employee.pastEmployments
        left join employee.roles
        where employee.id=:id  
    """)
    Optional<Employee> findByIdWithDetails(@Param("id") Integer id);

    @Query(value = """
        select employee
        from Employee employee
        left join fetch employee.roles
        where employee.username = :username
    """)
    Optional<Employee> findByUsernameWithRoles(@Param("username") String username);
}
