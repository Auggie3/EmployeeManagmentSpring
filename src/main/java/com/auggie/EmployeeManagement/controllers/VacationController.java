package com.auggie.EmployeeManagement.controllers;


import com.auggie.EmployeeManagement.dto.query.VacationQuery;
import com.auggie.EmployeeManagement.dto.query.VacationRequestQuery;
import com.auggie.EmployeeManagement.errorsAndValidation.ValidationActivator;
import com.auggie.EmployeeManagement.errorsAndValidation.ValidationException;
import com.auggie.EmployeeManagement.services.VacationService;
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
    @PreAuthorize(value = "@customAuthComponent.hasPermisionForVacations(authentication,#vacationQuery)")
    public ResponseEntity<Void> addVacation(@RequestBody VacationQuery vacationQuery) throws ValidationException {

        validationActivator.activateVacationValidator(vacationQuery);

        vacationService.addVacation(vacationQuery);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //because of the changes made, i can now use @Secured("ROLE_ADMIN) except custom
    //but its ok
    @DeleteMapping
    @PreAuthorize(value = "@customAuthComponent.hasPermisionForVacations(authentication,#vacationQuery)")
    public ResponseEntity<Void> deleteVacation(@RequestBody VacationQuery vacationQuery){
        vacationService.deleteVacation(vacationQuery);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("request")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<VacationRequestQuery>> findAllVacationRequests(){
        List<VacationRequestQuery> vacationRequestQueryList = vacationService.findAllVacationRequests();
        return new ResponseEntity<>(vacationRequestQueryList,HttpStatus.OK);
    }

    @PostMapping("request")
    public ResponseEntity<Void> requestVacation(@RequestBody VacationQuery vacationQuery) throws ValidationException{

        validationActivator.activateVacationValidator(vacationQuery);
        validationActivator.activateVacationRequestValidator(vacationQuery);

        vacationService.requestVacation(vacationQuery);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("request/allowed")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> allowVacationRequest(@RequestBody VacationQuery vacationQuery){
        vacationService.allowVacationRequest(vacationQuery);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("request/denied")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> denyVacationRequest(@RequestBody VacationQuery vacationQuery){

        vacationService.denyVacationRequest(vacationQuery);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
