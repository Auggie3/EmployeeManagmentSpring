package com.auggie.EmployeeManagement.controllers;


import com.auggie.EmployeeManagement.dto.command.VacationCommand;
import com.auggie.EmployeeManagement.dto.query.VacationQuery;
import com.auggie.EmployeeManagement.dto.query.VacationRequestQuery;
import com.auggie.EmployeeManagement.errorsAndValidation.ValidationActivator;
import com.auggie.EmployeeManagement.errorsAndValidation.ValidationException;
import com.auggie.EmployeeManagement.services.VacationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/employee/vacation")
public class VacationController {

    private final ValidationActivator validationActivator;
    private final VacationService vacationService;



    //because of the changes made, i can now use @Secured("ROLE_ADMIN) except custom
    //but its ok
    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> addVacation(@RequestBody @Valid VacationCommand vacationCommand) throws ValidationException {

        validationActivator.activateVacationValidator(vacationCommand);

        vacationService.addVacation(vacationCommand);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> deleteVacation(@RequestBody @Valid VacationCommand vacationCommand){
        vacationService.deleteVacation(vacationCommand);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("request")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<VacationRequestQuery>> findAllVacationRequests(){
        List<VacationRequestQuery> vacationRequestQueryList = vacationService.findAllVacationRequests();
        return new ResponseEntity<>(vacationRequestQueryList,HttpStatus.OK);
    }

    @PostMapping("request")
    public ResponseEntity<Void> requestVacation(@RequestBody @Valid VacationCommand vacationCommand) throws ValidationException{

        validationActivator.activateVacationValidator(vacationCommand);
        validationActivator.activateVacationRequestValidator(vacationCommand);

        vacationService.requestVacation(vacationCommand);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("request/allowed")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> allowVacationRequest(@RequestBody @Valid VacationCommand vacationCommand){
        vacationService.allowVacationRequest(vacationCommand);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("request/denied")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> denyVacationRequest(@RequestBody @Valid VacationCommand vacationCommand){

        vacationService.denyVacationRequest(vacationCommand);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
