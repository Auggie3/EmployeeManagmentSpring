package com.auggie.EmployeeManagement.errorsAndValidation.validators;

import com.auggie.EmployeeManagement.dto.command.RoleCreateCommand;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Locale;

@Component
public class RoleValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RoleCreateCommand.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RoleCreateCommand roleCreateCommand = (RoleCreateCommand) target;
        validateRoleName(roleCreateCommand.getName(), errors);
    }

    private void validateRoleName(String roleName, Errors errors){
        String roleNameUpperCase = roleName.toUpperCase(Locale.ROOT);
        if(!roleNameUpperCase.startsWith("ROLE_")){
            errors.rejectValue(
                    "name",
                    "role-invalid-name",
                    "Role name should start with 'ROLE_'"
            );
        }
    }
}
