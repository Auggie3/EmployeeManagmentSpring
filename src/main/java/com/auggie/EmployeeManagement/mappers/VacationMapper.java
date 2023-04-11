package com.auggie.EmployeeManagement.mappers;


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
    Vacation toVacation(VacationQuery vacationQuery);
    VacationQuery toVacationQuery(Vacation vacation);

    VacationRequest toVacationRequest(VacationQuery vacationQuery);
    VacationQuery toVacationQueryFromRequest(VacationRequest vacationRequest);

    VacationRequestQuery toVacationRequestQuery(VacationRequest vacationRequest);

}
