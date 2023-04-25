package com.auggie.EmployeeManagement.errorsAndValidation.validators;

import com.auggie.EmployeeManagement.dto.command.VacationCommand;
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
        return clazz.isAssignableFrom(VacationCommand.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        VacationCommand vacationCommand = (VacationCommand) target;
        validateDates(vacationCommand.getFrom(), vacationCommand.getTo(), errors, vacationCommand.getEmployeeId());
        if(!errors.hasErrors()) validateDaysOff(
                vacationCommand,
                errors
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

    private void validateDaysOff(VacationCommand vacationCommand, Errors errors) {
        if(vacationService.notEnoughFreeDays(vacationCommand)){
            errors.rejectValue(
                    "daysOff",
                    "not-enough-free-days",
                    "Requested more days off than there is available"
            );
        }
    }

//---------------------------
}
