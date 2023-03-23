package com.auggie.EmployeeManagement.entities.composite_primary_keys;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.io.Serializable;
import java.util.Date;

public class VacationCompositeKey implements Serializable {
    private Integer employeeId;
    private Date from;
}
