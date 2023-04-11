package com.auggie.EmployeeManagement.errorsAndValidation;

import com.auggie.EmployeeManagement.dto.ChangePasswordDTO;
import com.auggie.EmployeeManagement.dto.command.EmployeeCreateCommand;
import com.auggie.EmployeeManagement.dto.command.EmployeeUpdateCommand;
import com.auggie.EmployeeManagement.dto.command.RoleCreateCommand;
import com.auggie.EmployeeManagement.dto.query.PastEmploymentQuery;
import com.auggie.EmployeeManagement.dto.query.VacationQuery;
import com.auggie.EmployeeManagement.errorsAndValidation.validators.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ValidationActivator {

    private final VacationValidator vacationValidator;
    private final PastEmploymentValidator pastEmploymentValidator;
    private final RoleValidator roleValidator;
    private final ChangePasswordValidator changePasswordValidator;
    private final UsernameValidator usernameValidator;
    private final VacationRequestValidator vacationRequestValidator;


    public void activateVacationValidator(VacationQuery vacationQuery) throws ValidationException{
        activateValidator(vacationValidator,vacationQuery);
    }

    public void activatePastEmploymentValidator(PastEmploymentQuery pastEmploymentQuery) throws  ValidationException{
        activateValidator(pastEmploymentValidator, pastEmploymentQuery);
    }

    public void activateRoleValidator(RoleCreateCommand roleCreateCommand) throws ValidationException{
        activateValidator(roleValidator, roleCreateCommand);
    }

    public void activateChangePasswordValidator(ChangePasswordDTO changePasswordDTO) throws ValidationException{
        activateValidator(changePasswordValidator, changePasswordDTO);
    }

    public void activateUsernameValidator(Object object) throws ValidationException{
            activateValidator(usernameValidator, object);
    }

    public void activateVacationRequestValidator(VacationQuery vacationQuery) throws ValidationException{
        activateValidator(vacationRequestValidator, vacationQuery);
    }

    private void activateValidator(Validator validator, Object o) throws  ValidationException{
        Errors potentialErrors = new BeanPropertyBindingResult(o, "o");
        ValidationUtils.invokeValidator(validator, o, potentialErrors);

        if(potentialErrors.hasErrors()){
            throw new ValidationException(potentialErrors);
        }
    }
}
