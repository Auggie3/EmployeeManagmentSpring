package com.auggie.EmployeeManagement.dto.command;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class VacationCommand {
    @NotNull
    private Integer employeeId;
    @NotNull
    private LocalDate from;
    @NotNull
    private LocalDate to;
}
