package com.auggie.EmployeeManagement.errorsAndValidation;

import com.auggie.EmployeeManagement.dto.ChangePasswordDTO;
import com.auggie.EmployeeManagement.dto.command.RoleCreateCommand;
import com.auggie.EmployeeManagement.dto.command.VacationCommand;
import com.auggie.EmployeeManagement.dto.query.PastEmploymentQuery;
import com.auggie.EmployeeManagement.errorsAndValidation.validators.*;
import com.auggie.EmployeeManagement.interfaces.EmployeeCommandInterface;
import lombok.RequiredArgsConstructor;
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
    private final EmployeeCommandValidator employeeCommandValidator;
    private final VacationRequestValidator vacationRequestValidator;


    public void activateVacationValidator(VacationCommand vacationCommand) throws ValidationException{
        activateValidator(vacationValidator, vacationCommand);
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

    public void activateUsernameValidator(EmployeeCommandInterface usernameAndId) throws ValidationException{
            activateValidator(employeeCommandValidator, usernameAndId);
    }

    public void activateVacationRequestValidator(VacationCommand vacationCommand) throws ValidationException{
        activateValidator(vacationRequestValidator, vacationCommand);
    }

    private void activateValidator(Validator validator, Object o) throws  ValidationException{
        Errors potentialErrors = new BeanPropertyBindingResult(o, "o");
        ValidationUtils.invokeValidator(validator, o, potentialErrors);

        if(potentialErrors.hasErrors()){
            throw new ValidationException(potentialErrors);
        }
    }
}
