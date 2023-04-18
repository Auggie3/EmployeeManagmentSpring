package com.auggie.EmployeeManagement.filters;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeFilter {
    private String name;
    private String position;
    private List<String> roles;
}
