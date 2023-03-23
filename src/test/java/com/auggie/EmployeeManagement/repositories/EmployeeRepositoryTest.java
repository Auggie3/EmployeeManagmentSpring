package com.auggie.EmployeeManagement.repositories;

import com.auggie.EmployeeManagement.EmployeeManagementApplication;
import com.auggie.EmployeeManagement.entities.Employee;
import com.auggie.EmployeeManagement.entities.PastEmployment;
import com.auggie.EmployeeManagement.entities.Role;
import com.auggie.EmployeeManagement.entities.Vacation;
import com.mysql.cj.log.Log;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EmployeeManagementApplication.class)
@Log4j2
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @Transactional
    void shouldDoSomething(){
        List<Employee> employeeList = employeeRepository.findAll();
        log.info("{}",employeeList);
    }

    @Test
    @Transactional
    void shouldAddNewEmployeeAndNewRole(){
        Employee e = new Employee();
        e.setName("TestName3");
        e.setPosition("TestPosition2");
        e.setStartDate(new Date());
        e.setVacationDaysAvailable(22f);
        e.setVacationDaysPerYear(22f);

        Role r = new Role();
        r.addEmployee(e);
        r.setName("ROLE_TEST3");
        r.setDescription("Role description test2");
        e.addRole(r);
        log.info("Role: {}",r);
        log.info("Employee: {}", e);

        employeeRepository.save(e);
    }


    @Test
    @Transactional
    void shouldAddExistingRoleToExistingEmployee(){
        Role r = roleRepository.findByName("ROLE_USER");

        Optional<Employee> optionalEmployeee = employeeRepository.findById(8);
        Employee e = optionalEmployeee.orElseThrow(EntityNotFoundException::new);

        e.addRole(r);
        r.addEmployee(e);

        try {
            employeeRepository.save(e);
        }catch (Exception exception){
            assertThat(exception.getClass()).isEqualTo(DataIntegrityViolationException.class);
        }

    }

    @Test
    @Transactional
    public void shouldAddNewVacation(){
        Employee e = employeeRepository.findById(1).orElseThrow(EntityNotFoundException::new);
        Vacation v = new Vacation();
        v.setDaysOff(1f);
        v.setFrom(LocalDate.parse("2000-11-11").toDate());
        v.setTo(new Date());
        v.setEmployeeId(e.getId());

        e.addVacation(v);

        employeeRepository.save(e);

    }

    @Test
    @Transactional
    public void shouldAddNewPastEmployment(){
        Employee e = employeeRepository.findById(8).orElseThrow(EntityNotFoundException::new);
        PastEmployment p = new PastEmployment();
        p.setCompanyName("CompanyTest");
        p.setFrom(LocalDate.parse("2011-11-11").toDate());
        p.setTo(LocalDate.parse("2012-11-12").toDate());
        p.setEmployeeId(8);

        e.addPastEmployment(p);

        employeeRepository.save(e);

    }

}
