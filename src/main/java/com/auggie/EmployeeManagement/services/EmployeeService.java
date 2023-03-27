package com.auggie.EmployeeManagement.services;

import com.auggie.EmployeeManagement.dto.EmployeeRoleDTO;
import com.auggie.EmployeeManagement.dto.command.EmployeeCreateCommand;
import com.auggie.EmployeeManagement.dto.command.EmployeeUpdateCommand;
import com.auggie.EmployeeManagement.dto.query.*;
import com.auggie.EmployeeManagement.entities.Employee;
import com.auggie.EmployeeManagement.entities.PastEmployment;
import com.auggie.EmployeeManagement.entities.Role;
import com.auggie.EmployeeManagement.entities.Vacation;
import com.auggie.EmployeeManagement.mappers.EmployeeMapper;
import com.auggie.EmployeeManagement.mappers.RoleMapper;
import com.auggie.EmployeeManagement.repositories.EmployeeRepository;
import com.auggie.EmployeeManagement.repositories.RoleRepository;
import com.mysql.cj.log.Log;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;


    public List<EmployeeQuery> findAll() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toEmployeeQuery)
                .toList();
    }


    public List<EmployeeDetailsQuery> findAllWithDetails() {
        return employeeRepository.findAllWithDetails()
                .stream()
                .map(employeeMapper::toEmployeeDetailsQuery)
                .toList();
    }

    public EmployeeQuery findById(Integer id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        Employee employee = optionalEmployee.orElseThrow(EntityNotFoundException::new);
        return employeeMapper.toEmployeeQuery(employee);
    }

    public EmployeeDetailsQuery findByIdWithDetails(Integer id){
        Optional<Employee> optionalEmployee = employeeRepository.findByIdWithDetails(id);
        Employee employee = optionalEmployee.orElseThrow(EntityNotFoundException::new);
        return  employeeMapper.toEmployeeDetailsQuery(employee);
    }

    public EmployeeQuery createEmployee(EmployeeCreateCommand employeeCreateCommand) {
        Employee employee = employeeMapper.toEmployee(employeeCreateCommand);
        Role userRole = roleRepository.findByName("ROLE_USER");
        employee.addRole(userRole);
        employeeRepository.save(employee);
        return employeeMapper.toEmployeeQuery(employee);
    }

    public void updateEmployee(EmployeeUpdateCommand employeeUpdateCommand) {
        Employee employee = employeeMapper.toEmployeeUpdate(employeeUpdateCommand);
        Employee employeeInDatabase = employeeRepository.getReferenceById(employee.getId());
        employeeInDatabase.copyEmployee(employee);
        employeeRepository.save(employeeInDatabase);
    }

    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }

    public void addPastEmployment(PastEmploymentQuery pastEmploymentQuery) {
        PastEmployment pastEmployment = employeeMapper.toPastEmployment(pastEmploymentQuery);
        Employee employee = employeeRepository.getReferenceById(pastEmployment.getEmployeeId());
        employee.addPastEmployment(pastEmployment);
        employeeRepository.save(employee);
    }

    public void deletePastEmployment(PastEmploymentQuery pastEmploymentQuery){
        PastEmployment pastEmployment = employeeMapper.toPastEmployment(pastEmploymentQuery);
        Employee employee = employeeRepository.getReferenceById(pastEmployment.getEmployeeId());
        employee.deletePastEmployment(pastEmployment);
        employeeRepository.save(employee);
    }

    public void addVacation(VacationQuery vacationQuery) {
        Vacation vacation = employeeMapper.toVacation(vacationQuery);
        Employee employee = employeeRepository.getReferenceById(vacation.getEmployeeId());

        float daysAvailable = employee.getVacationDaysAvailable();
        daysAvailable-=vacation.getDaysOff();
        employee.setVacationDaysAvailable(daysAvailable);

        employee.addVacation(vacation);
        employeeRepository.save(employee);
    }

    public void deleteVacation(VacationQuery vacationQuery) {
        Vacation vacation = employeeMapper.toVacation(vacationQuery);
        Employee employee = employeeRepository.getReferenceById(vacation.getEmployeeId());

//        log.info("Date test: {}",employee.getVacations());
//        log.info("Date test: {}",vacationQuery.getFrom());

        float daysAvailable = employee.getVacationDaysAvailable();
        daysAvailable+=vacation.getDaysOff();
        employee.setVacationDaysAvailable(daysAvailable);

        employee.deleteVacation(vacation);
        employeeRepository.save(employee);


    }

    public void addRole(EmployeeRoleDTO employeeRoleDTO) {
        Employee employee = employeeRepository.getReferenceById(employeeRoleDTO.getEmployeeId());
        Role role = roleRepository.findByName(employeeRoleDTO.getRoleName());
        employee.addRole(role);
        employeeRepository.save(employee);
    }

    public void removeRole(EmployeeRoleDTO employeeRoleDTO){
        Employee employee = employeeRepository.getReferenceById(employeeRoleDTO.getEmployeeId());
        Role role = roleRepository.findByName(employeeRoleDTO.getRoleName());
        employee.removeRole(role);
        employeeRepository.save(employee);
    }

    public List<RoleQuery> findRoles(Integer id) {
        Employee employee = employeeRepository.getReferenceById(id);
        Set<Role> roleEntities = employee.getRoles();
        return roleEntities
                .stream()
                .map(roleMapper::toRoleQuery)
                .toList();
    }
}
