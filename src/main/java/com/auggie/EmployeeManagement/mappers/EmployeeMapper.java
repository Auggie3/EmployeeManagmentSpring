package com.auggie.EmployeeManagement.mappers;

import com.auggie.EmployeeManagement.dto.command.EmployeeCreateCommand;
import com.auggie.EmployeeManagement.dto.command.EmployeeUpdateCommand;
import com.auggie.EmployeeManagement.dto.command.RoleCreateCommand;
import com.auggie.EmployeeManagement.dto.query.*;
import com.auggie.EmployeeManagement.entities.Employee;
import com.auggie.EmployeeManagement.entities.PastEmployment;
import com.auggie.EmployeeManagement.entities.Role;
import com.auggie.EmployeeManagement.entities.Vacation;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface EmployeeMapper {

    VacationQuery toVacationQuery(Vacation vacation);
    PastEmploymentQuery toPastEmploymentQuery(PastEmployment pastEmployment);
    EmployeeQuery toEmployeeQuery(Employee employee);
    EmployeeDetailsQuery toEmployeeDetailsQuery(Employee employee);

    Employee toEmployee(EmployeeCreateCommand employeeCreateCommand);
    Employee toEmployeeUpdate(EmployeeUpdateCommand employeeUpdateCommand);
    Vacation toVacation(VacationQuery vacationQuery);
    PastEmployment toPastEmployment(PastEmploymentQuery pastEmploymentQuery);

}
