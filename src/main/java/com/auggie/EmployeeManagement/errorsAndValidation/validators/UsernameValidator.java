package com.auggie.EmployeeManagement.errorsAndValidation.validators;

import com.auggie.EmployeeManagement.dto.command.EmployeeCreateCommand;
import com.auggie.EmployeeManagement.dto.command.EmployeeUpdateCommand;
import com.auggie.EmployeeManagement.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UsernameValidator implements Validator {

    private final EmployeeService employeeService;


    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(EmployeeCreateCommand.class) || clazz.isAssignableFrom(EmployeeUpdateCommand.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if(target.getClass()==EmployeeCreateCommand.class) {
            String username = ((EmployeeCreateCommand) target).getUsername();
            validateUsername(username, errors);
        }

        if(target.getClass()==EmployeeUpdateCommand.class) {
            EmployeeUpdateCommand employeeUpdateCommand = ((EmployeeUpdateCommand) target);
            String username = employeeUpdateCommand.getUsername();
            Integer id = employeeUpdateCommand.getId();
            validateUsernameWithId(username, errors, id);
        }

    }

    private void validateUsernameWithId(String username, Errors errors, Integer id) {
        if(employeeService.findIdOfUsername(username)!=id){
            validateUsername(username, errors);
        }
    }

    private void validateUsername(String username, Errors errors){
        if(employeeService.usernameExists(username)){
            errors.rejectValue(
                    "username",
                    "username-exists-already",
                    "Username already exists!!!"
                    );
        }
    }
}
