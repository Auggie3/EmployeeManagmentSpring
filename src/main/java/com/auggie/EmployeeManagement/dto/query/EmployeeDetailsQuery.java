package com.auggie.EmployeeManagement.dto.query;

import com.auggie.EmployeeManagement.entities.PastEmployment;
import com.auggie.EmployeeManagement.entities.Role;
import com.auggie.EmployeeManagement.entities.Vacation;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@Data
@NoArgsConstructor
public class EmployeeDetailsQuery {
    private Integer id;
    private String name;
    private LocalDate startDate;
    private String position;
    private float vacationDaysPerYear;
    private float vacationDaysAvailable;
    private List<VacationQuery> vacations;
    private List<PastEmploymentQuery> pastEmployments;
    private Set<RoleQuery> roles;
    private String username;
}
