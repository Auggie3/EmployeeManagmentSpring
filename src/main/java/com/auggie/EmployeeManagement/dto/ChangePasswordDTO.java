package com.auggie.EmployeeManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangePasswordDTO {
    @NotNull
    @NotBlank
    String newPassword;

    @NotNull
    @NotBlank
    String oldPassword;
}
