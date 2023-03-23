package com.auggie.EmployeeManagement.dto.command;

import lombok.Data;

import java.util.Date;

@Data
public class EmployeeUpdateCommand {
    private Integer id;
    private String name;
    private Date startDate;
    private String position;
    private float vacationDaysPerYear;
    private float vacationDaysAvailable;
}
