package com.auggie.EmployeeManagement.dto.command;

import com.auggie.EmployeeManagement.dto.query.PastEmploymentQuery;
import com.auggie.EmployeeManagement.dto.query.RoleQuery;
import com.auggie.EmployeeManagement.dto.query.VacationQuery;
import com.auggie.EmployeeManagement.entities.PastEmployment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class EmployeeCreateCommand {
    @NotNull
    @NotBlank
    private String name;

    private LocalDate startDate;
    private String position;

    @PositiveOrZero
    private float vacationDaysPerYear;

    @PositiveOrZero
    private float vacationDaysAvailable;
}
