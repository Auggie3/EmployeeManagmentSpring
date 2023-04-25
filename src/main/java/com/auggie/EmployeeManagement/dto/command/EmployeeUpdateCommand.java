package com.auggie.EmployeeManagement.dto.command;

import com.auggie.EmployeeManagement.interfaces.EmployeeCommandInterface;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeUpdateCommand implements EmployeeCommandInterface {
    @NotNull
    private Integer id;
    @NotNull
    @NotBlank
    private String name;

    private LocalDate startDate;
    private LocalDate endDate;
    private String position;


    @NotNull
    @NotBlank
    private String username;
}
