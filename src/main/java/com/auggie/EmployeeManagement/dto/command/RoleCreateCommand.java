package com.auggie.EmployeeManagement.dto.command;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleCreateCommand {
    private String name;
    private String description;
}
