package com.auggie.EmployeeManagement.mappers;


import com.auggie.EmployeeManagement.dto.command.VacationCommand;
import com.auggie.EmployeeManagement.dto.query.VacationQuery;
import com.auggie.EmployeeManagement.dto.query.VacationRequestQuery;
import com.auggie.EmployeeManagement.entities.Vacation;
import com.auggie.EmployeeManagement.entities.VacationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface VacationMapper {
    VacationQuery toVacationQuery(Vacation vacation);

    VacationCommand toVacationCommandFromRequest(VacationRequest vacationRequest);

    VacationRequestQuery toVacationRequestQuery(VacationRequest vacationRequest);

    Vacation toVacationFromCommand(VacationCommand vacationCommand);

    VacationRequest toVacationRequestFromCommand(VacationCommand vacationCommand);
}
