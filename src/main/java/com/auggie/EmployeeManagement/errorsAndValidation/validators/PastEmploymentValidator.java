package com.auggie.EmployeeManagement.errorsAndValidation.validators;

import com.auggie.EmployeeManagement.dto.query.PastEmploymentQuery;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class PastEmploymentValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(PastEmploymentQuery.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PastEmploymentQuery pastEmploymentQuery = (PastEmploymentQuery) target;
        validateDates(pastEmploymentQuery.getFrom(),pastEmploymentQuery.getTo(),errors);
    }

    private void validateDates(LocalDate from, LocalDate to, Errors errors) {
        if(from.isAfter(to)){
            errors.rejectValue(
                    "to",
                    "past-employment-date-from-after-to",
                    "'From' date cannot be after 'to' date!"
            );
        }
    }
}
