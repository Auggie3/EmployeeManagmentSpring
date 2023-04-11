package com.auggie.EmployeeManagement.errorsAndValidation.validators;

import com.auggie.EmployeeManagement.dto.query.VacationQuery;
import com.auggie.EmployeeManagement.services.VacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class VacationValidator implements Validator {

    private final VacationService vacationService;


    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(VacationQuery.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        VacationQuery vacationQuery = (VacationQuery) target;
        validateDates(vacationQuery.getFrom(),vacationQuery.getTo(), errors, vacationQuery.getEmployeeId());
        if(!errors.hasErrors()) validateDaysOff(
                vacationQuery.getFrom(),
                vacationQuery.getTo(),
                vacationQuery.getDaysOff(),
                errors,
                vacationQuery.getEmployeeId()
        );
    }

    private void validateDates(LocalDate from, LocalDate to, Errors errors, Integer id){
        if( to != null && from.isAfter(to)){
            errors.rejectValue(
                    "to",
                    "vacation-to-after-from",
                    "'From' date cannot be after 'to' date!"
            );
        }

        if(vacationService.overlapExists(from,to,id)){
            errors.rejectValue(
                    "from",
                    "vacation-overlap",
                    "Vacation overlaps with another vacation"
            );
            errors.rejectValue(
                    "to",
                    "vacation-overlap",
                    "Vacation overlaps with another vacation"
            );
        }
    }

    private void validateDaysOff(LocalDate from,
                                 LocalDate to,
                                 float daysOff,
                                 Errors errors,
                                 Integer id)
    {
        if(vacationService.notEnoughFreeDays(id,daysOff)){
            errors.rejectValue(
                    "daysOff",
                    "not-enough-free-days",
                    "Requested more days off than there is available"
            );
        }

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
