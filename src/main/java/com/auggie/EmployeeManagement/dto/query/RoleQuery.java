package com.auggie.EmployeeManagement.dto.query;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleQuery {

    private Integer id;
    private String name;
    private String description;

}
