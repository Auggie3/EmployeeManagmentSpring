package com.auggie.EmployeeManagement.dto.query;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class VacationRequestQuery {
    private Integer employeeId;
    private String employeeName;
    private LocalDate from;
    private LocalDate to;
    private float daysOff;
}
