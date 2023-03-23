package com.auggie.EmployeeManagement.mappers;

import com.auggie.EmployeeManagement.dto.command.RoleCreateCommand;
import com.auggie.EmployeeManagement.dto.query.RoleQuery;
import com.auggie.EmployeeManagement.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface RoleMapper {

    RoleQuery toRoleQuery(Role role);
    Role toRole(RoleCreateCommand roleCreateCommand);
}
