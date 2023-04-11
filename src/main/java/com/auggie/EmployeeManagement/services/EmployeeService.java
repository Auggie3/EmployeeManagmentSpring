package com.auggie.EmployeeManagement.services;

import com.auggie.EmployeeManagement.configurations.CacheHelper;
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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
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
    private final CacheHelper cacheHelper;

    @Cacheable(value = "employees", key = "'all-employees'")
    public List<EmployeeQuery> findAll() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toEmployeeQuery)
                .toList();
    }

    @Cacheable(value = "employees", key = "'all-employees-details'")
    public List<EmployeeDetailsQuery> findAllWithDetails() {
        return employeeRepository.findAllWithDetails()
                .stream()
                .map(employeeMapper::toEmployeeDetailsQuery)
                .toList();
    }

    @Cacheable(value = "employee", key = "#id")
    public EmployeeQuery findById(Integer id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        Employee employee = optionalEmployee.orElseThrow(EntityNotFoundException::new);
        return employeeMapper.toEmployeeQuery(employee);
    }

    @Cacheable(value = "employee-details", key = "#id")
    public EmployeeDetailsQuery findByIdWithDetails(Integer id){
        Optional<Employee> optionalEmployee = employeeRepository.findByIdWithDetails(id);
        Employee employee = optionalEmployee.orElseThrow(EntityNotFoundException::new);
        return  employeeMapper.toEmployeeDetailsQuery(employee);
    }

    @CachePut(cacheNames = "employee", key = "#result.id")
    public EmployeeQuery createEmployee(EmployeeCreateCommand employeeCreateCommand) {
        cacheHelper.evictAllEntriesFromCache(); //CACHE

        String password = employeeCreateCommand.getPassword();
        String encryptedPassword = new BCryptPasswordEncoder().encode(password);
        employeeCreateCommand.setPassword(encryptedPassword);

        Employee employee = employeeMapper.toEmployee(employeeCreateCommand);
        Role userRole = roleRepository.findByName("ROLE_USER");
        employee.addRole(userRole);
        employeeRepository.save(employee);
        return employeeMapper.toEmployeeQuery(employee);
    }

    public void updateEmployee(EmployeeUpdateCommand employeeUpdateCommand) {
        cacheHelper.evictAllEntriesFromCache(); //CACHE

        Employee employee = employeeMapper.toEmployeeUpdate(employeeUpdateCommand);
        Employee employeeInDatabase = employeeRepository.getReferenceById(employee.getId());

        employeeInDatabase.copyEmployee(employee);
        employeeRepository.save(employeeInDatabase);
    }

    public void deleteEmployee(Integer id) {
        cacheHelper.evictAllEntriesFromCache(); //CACHE
        employeeRepository.deleteById(id);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "employees", key = "'all-employees-details'"),
                    @CacheEvict(value = "employee-details", key = "#pastEmploymentQuery.getEmployeeId()")
            }
    )
    public void addPastEmployment(PastEmploymentQuery pastEmploymentQuery) {
        PastEmployment pastEmployment = employeeMapper.toPastEmployment(pastEmploymentQuery);
        Employee employee = employeeRepository.getReferenceById(pastEmployment.getEmployeeId());
        employee.addPastEmployment(pastEmployment);
        employeeRepository.save(employee);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "employees", key = "'all-employees-details'"),
                    @CacheEvict(value = "employee-details", key = "#pastEmploymentQuery.getEmployeeId()")
            }
    )
    public void deletePastEmployment(PastEmploymentQuery pastEmploymentQuery){
        PastEmployment pastEmployment = employeeMapper.toPastEmployment(pastEmploymentQuery);
        Employee employee = employeeRepository.getReferenceById(pastEmployment.getEmployeeId());
        employee.deletePastEmployment(pastEmployment);
        employeeRepository.save(employee);
    }


    @Caching(
            evict = {
                    @CacheEvict(value = "employees", key = "'all-employees-details'"),
                    @CacheEvict(value = "employee-details", key = "#employeeRoleDTO.getEmployeeId()")
            }
    )
    public void addRole(EmployeeRoleDTO employeeRoleDTO) {
        Employee employee = employeeRepository.getReferenceById(employeeRoleDTO.getEmployeeId());
        Role role = roleRepository.findByName(employeeRoleDTO.getRoleName());
        employee.addRole(role);
        employeeRepository.save(employee);
    }


    @Caching(
            evict = {
                    @CacheEvict(value = "employees", key = "'all-employees-details'"),
                    @CacheEvict(value = "employee-details", key = "#employeeRoleDTO.getEmployeeId()")
            }
    )
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

    public Optional<Employee> findByUsernameWithRoles(String username) {
        return employeeRepository.findByUsernameWithRoles(username);
    }

    public Integer findIdOfUsername(String username) {
        return employeeRepository.findIdOfUsername(username);
    }

    public void changePassword(String password, Integer id) {
        Employee employee = employeeRepository.getReferenceById(id);
        employee.changePassword(password);
        employeeRepository.save(employee);
    }

    public Boolean checkOldPassword(String oldPassword) {
        Integer id = (Integer) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Employee employee = employeeRepository.getReferenceById(id);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(oldPassword, employee.getPassword());
    }

    public boolean usernameExists(String username) {
        if(findIdOfUsername(username)!=null) return true;
        return false;
    }
}
