package com.auggie.EmployeeManagement.dto.command;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class VacationCreateCommand {
    private Integer employeeId;
    private LocalDate from;
    private LocalDate to;
}
