package com.auggie.EmployeeManagement.interfaces;

import java.time.LocalDate;

public interface EmployeeCommandInterface {
    String getUsername();
    Integer getId();
    LocalDate getStartDate();
    LocalDate getEndDate();
}
