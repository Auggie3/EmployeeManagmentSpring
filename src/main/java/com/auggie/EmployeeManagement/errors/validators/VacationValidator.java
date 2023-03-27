package com.auggie.EmployeeManagement.errors.validators;

import com.auggie.EmployeeManagement.dto.query.VacationQuery;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
        if(!errors.hasErrors()) validateDaysOff(
                vacationQuery.getFrom(),
                vacationQuery.getTo(),
                vacationQuery.getDaysOff(),
                errors
        );
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

    private void validateDaysOff(LocalDate from, LocalDate to, float daysOff, Errors errors){
        if(to==null){
            if(daysOff == 0.5 || daysOff == 1) return;
            errors.rejectValue(
                    "daysOff",
                    "daysOff-differ-from-dates",
                    "'daysOff' should correspond to given dates!"
            );
            return;
        }

        long datesDifference = ChronoUnit.DAYS.between(from,to);
        if( ( (long)daysOff ) != datesDifference){
            errors.rejectValue(
                    "daysOff",
                    "daysOff-differ-from-dates",
                    "'daysOff' should correspond to given dates!"
            );
        }

    }

//---------------------------
}
