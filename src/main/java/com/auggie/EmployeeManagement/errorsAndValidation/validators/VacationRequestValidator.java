package com.auggie.EmployeeManagement.errorsAndValidation.validators;

import com.auggie.EmployeeManagement.dto.command.VacationCommand;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class VacationRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(VacationCommand.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        VacationCommand vacationCommand = (VacationCommand) target;
        validateRequestDate(vacationCommand.getFrom(), errors);
    }

    private void validateRequestDate(LocalDate from, Errors errors){
        if(from.isBefore(LocalDate.now())){
            errors.rejectValue(
                    "from",
                    "date-in-past",
                    "Cannot request vacation date in past"
            );
        }
    }
}
