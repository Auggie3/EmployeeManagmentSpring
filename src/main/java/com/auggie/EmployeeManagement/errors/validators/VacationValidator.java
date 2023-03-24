package com.auggie.EmployeeManagement.errors.validators;

import com.auggie.EmployeeManagement.dto.query.VacationQuery;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class VacationValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(VacationQuery.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        VacationQuery vacationQuery = (VacationQuery) target;
        validateDates(vacationQuery.getFrom(),vacationQuery.getTo(), errors);
    }

    private void validateDates(LocalDate from, LocalDate to, Errors errors){
        if(from.isAfter(to)){
            errors.rejectValue(
                    "to",
                    "vacation-to-after-from",
                    "'From' date cannot be after 'to' date!"
            );
        }
    }
}
