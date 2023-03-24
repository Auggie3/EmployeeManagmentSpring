package com.auggie.EmployeeManagement.dto.query;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
public class VacationQuery {
    private Integer employeeId;
    private LocalDate from;
    private LocalDate to;
    private float daysOff;
}
