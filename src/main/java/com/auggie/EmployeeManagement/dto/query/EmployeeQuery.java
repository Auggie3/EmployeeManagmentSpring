package com.auggie.EmployeeManagement.dto.query;

import com.auggie.EmployeeManagement.entities.PastEmployment;
import com.auggie.EmployeeManagement.entities.Role;
import com.auggie.EmployeeManagement.entities.Vacation;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
public class EmployeeQuery {
    private Integer id;
    private String name;
    private Date startDate;
    private String position;
    private float vacationDaysPerYear;
    private float vacationDaysAvailable;
}
