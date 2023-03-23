package com.auggie.EmployeeManagement.entities.composite_primary_keys;

import java.io.Serializable;
import java.util.Date;

public class PastEmploymentCompositeKey implements Serializable {
    private Integer employeeId;
    private String companyName;
    private Date from;
}
