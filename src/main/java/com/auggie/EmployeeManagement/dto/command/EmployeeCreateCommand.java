package com.auggie.EmployeeManagement.dto.command;

import com.auggie.EmployeeManagement.interfaces.EmployeeCommandInterface;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class EmployeeCreateCommand implements EmployeeCommandInterface {
    @NotNull
    @NotBlank
    private String name;

    private LocalDate startDate;
    private LocalDate endDate;
    private String position;

    @NotNull
    @NotBlank
    private String username;

    @NotBlank
    private String password = "11111111";

    @Override
    public Integer getId() {
        return null;
    }
}
