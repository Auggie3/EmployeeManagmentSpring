package com.auggie.EmployeeManagement.errorsAndValidation.validators;

import com.auggie.EmployeeManagement.dto.ChangePasswordDTO;
import com.auggie.EmployeeManagement.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ChangePasswordValidator implements Validator {

    @Autowired
    private EmployeeService employeeService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(ChangePasswordDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ChangePasswordDTO changePasswordDTO = (ChangePasswordDTO) target;
        validateOldPassword(changePasswordDTO.getOldPassword(), errors);
    }

    public void validateOldPassword(String oldPassword, Errors errors){
        Boolean b = employeeService.checkOldPassword(oldPassword);
        if(!b){
            errors.rejectValue(
                    "oldPassword",
                    "wrong-password",
                    "Wrong password!"
            );
        }
    }
}
