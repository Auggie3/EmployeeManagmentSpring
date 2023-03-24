package com.auggie.EmployeeManagement.errors.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorDto {
    private final String fieldName;
    private final Object rejectedValue;
    private final String message;
}
