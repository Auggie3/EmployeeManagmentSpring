package com.auggie.EmployeeManagement.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class EmployeeUpdateCommand {
    @NotNull
    private Integer id;
    @NotNull
    @NotBlank
    private String name;

    private LocalDate startDate;
    private String position;

    @PositiveOrZero
    private float vacationDaysPerYear;

    @PositiveOrZero
    private float vacationDaysAvailable;

    @NotNull
    @NotBlank
    private String username;
}
