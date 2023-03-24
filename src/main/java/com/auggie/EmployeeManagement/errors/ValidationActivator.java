package com.auggie.EmployeeManagement.errors;

import com.auggie.EmployeeManagement.dto.query.PastEmploymentQuery;
import com.auggie.EmployeeManagement.dto.query.VacationQuery;
import com.auggie.EmployeeManagement.errors.validators.PastEmploymentValidator;
import com.auggie.EmployeeManagement.errors.validators.VacationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ValidationActivator {

    @Autowired
    private VacationValidator vacationValidator;

    @Autowired
    private PastEmploymentValidator pastEmploymentValidator;


    public void activateVacationValidator(VacationQuery vacationQuery) throws ValidationException{
        activateValidator(vacationValidator,vacationQuery);
    }

    public void activatePastEmploymentValidator(PastEmploymentQuery pastEmploymentQuery) throws  ValidationException{
        activateValidator(pastEmploymentValidator, pastEmploymentQuery);
    }


    private void activateValidator(Validator validator, Object o) throws  ValidationException{
        Errors potentialErrors = new BeanPropertyBindingResult(o, "o");
        ValidationUtils.invokeValidator(validator, o, potentialErrors);

        if(potentialErrors.hasErrors()){
            throw new ValidationException(potentialErrors);
        }
    }
}
