package com.auggie.EmployeeManagement.dto.command;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class EmployeeUpdateCommand {
    private Integer id;
    private String name;
    private LocalDate startDate;
    private String position;
    private float vacationDaysPerYear;
    private float vacationDaysAvailable;
}
