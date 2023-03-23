package com.auggie.EmployeeManagement.dto.command;

import com.auggie.EmployeeManagement.dto.query.PastEmploymentQuery;
import com.auggie.EmployeeManagement.dto.query.RoleQuery;
import com.auggie.EmployeeManagement.dto.query.VacationQuery;
import com.auggie.EmployeeManagement.entities.PastEmployment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class EmployeeCreateCommand {
    private String name;
    private Date startDate;
    private String position;
    private float vacationDaysPerYear;
    private float vacationDaysAvailable;
}
