package com.auggie.EmployeeManagement.entities.composite_primary_keys;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
public class VacationCompositeKey implements Serializable {
    private Integer employeeId;
    private LocalDate from;
}
