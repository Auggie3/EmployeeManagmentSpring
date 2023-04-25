package com.auggie.EmployeeManagement.errorsAndValidation.validators;

import com.auggie.EmployeeManagement.interfaces.EmployeeCommandInterface;
import com.auggie.EmployeeManagement.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class EmployeeCommandValidator implements Validator {

    private final EmployeeService employeeService;


    @Override
    public boolean supports(Class<?> clazz) {
        return EmployeeCommandInterface.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        EmployeeCommandInterface employeeCommand = (EmployeeCommandInterface) target;
        String username = employeeCommand.getUsername();

        if(employeeCommand.getId() == null){
            validateUsername(username, errors);
        }
        else {
            Integer id = employeeCommand.getId();
            validateUsernameWithId(username, errors, id);
        }

        validateDates(employeeCommand.getStartDate(), employeeCommand.getEndDate(), errors);

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

    private void validateDates(LocalDate start, LocalDate end, Errors errors){
        if(end != null){
            if(end.isBefore(start)){
                errors.rejectValue(
                        "endDate",
                        "end-date-before-start-date",
                        "Employee end date cant be before start date"
                );
            }
        }
    }
}
