package com.auggie.EmployeeManagement.services;

import com.auggie.EmployeeManagement.configurations.CacheHelper;
import com.auggie.EmployeeManagement.dto.command.VacationCommand;
import com.auggie.EmployeeManagement.dto.query.VacationQuery;
import com.auggie.EmployeeManagement.dto.query.VacationRequestQuery;
import com.auggie.EmployeeManagement.entities.Employee;
import com.auggie.EmployeeManagement.entities.Vacation;
import com.auggie.EmployeeManagement.entities.VacationRequest;
import com.auggie.EmployeeManagement.entities.composite_primary_keys.VacationCompositeKey;
import com.auggie.EmployeeManagement.mappers.VacationMapper;
import com.auggie.EmployeeManagement.repositories.EmployeeRepository;
import com.auggie.EmployeeManagement.repositories.VacationRequestRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacationService {

    private final EmployeeRepository employeeRepository;
    private final VacationMapper vacationMapper;
    private final VacationRequestRepository vacationRequestRepository;
    private final CacheHelper cacheHelper;


    @Caching(
            evict = {
                    @CacheEvict(value = "employees", key = "'all-employees-details'"),
                    @CacheEvict(value = "employee-details", key = "#vacationCommand.getEmployeeId()")
            }
    )
    public void addVacation(VacationCommand vacationCommand) {
        Vacation vacation = vacationMapper.toVacationFromCommand(vacationCommand);
        Employee employee = employeeRepository.getReferenceById(vacation.getEmployeeId());

        employee.addVacation(vacation);
        employeeRepository.save(employee);
    }


    @Caching(
            evict = {
                    @CacheEvict(value = "employees", key = "'all-employees-details'"),
                    @CacheEvict(value = "employee-details", key = "#vacationCommand.getEmployeeId()")
            }
    )
    public void deleteVacation(VacationCommand vacationCommand) {
        Vacation vacation = vacationMapper.toVacationFromCommand(vacationCommand);
        Employee employee = employeeRepository.getReferenceById(vacation.getEmployeeId());


        employee.deleteVacation(vacation);
        employeeRepository.save(employee);


    }

    public void requestVacation(VacationCommand vacationCommand) {
        VacationRequest vacationRequest = vacationMapper.toVacationRequestFromCommand(vacationCommand);
        vacationRequestRepository.save(vacationRequest);
    }

    public List<VacationRequestQuery> findAllVacationRequests() {
        return vacationRequestRepository.findAll()
                .stream()
                .map(vacationRequest -> {
                    VacationRequestQuery vacationRequestQuery = vacationMapper.toVacationRequestQuery(vacationRequest);
                    Employee employee = employeeRepository.getReferenceById(vacationRequest.getEmployeeId());
                    vacationRequestQuery.setEmployeeName(employee.getName());

                    return vacationRequestQuery;
                })
                .toList();
    }

    public void allowVacationRequest(VacationCommand vacationCommand) {
        VacationCompositeKey vacationCompositeKey = new VacationCompositeKey();
        vacationCompositeKey.setEmployeeId(vacationCommand.getEmployeeId());
        vacationCompositeKey.setFrom(vacationCommand.getFrom());
        VacationRequest vacationRequest = vacationRequestRepository.findById(vacationCompositeKey)
                .orElseThrow(EntityNotFoundException::new);

        //TODO: maybe i dont need this because i already have vacationQuery but just for safety...
        VacationCommand allowedVacation = vacationMapper.toVacationCommandFromRequest(vacationRequest);

        //CACHE: TODO: u should separate vacation and vacation request services into 2 classes
        cacheHelper.evictAllEntriesFromCache();

        addVacation(allowedVacation);

        vacationRequestRepository.delete(vacationRequest);
    }

    public void denyVacationRequest(VacationCommand vacationCommand) {
        VacationCompositeKey vacationCompositeKey = new VacationCompositeKey();
        vacationCompositeKey.setEmployeeId(vacationCommand.getEmployeeId());
        vacationCompositeKey.setFrom(vacationCommand.getFrom());

        VacationRequest vacationRequest = vacationRequestRepository.getReferenceById(vacationCompositeKey);

        vacationRequestRepository.delete(vacationRequest);
    }

    //TODO: Maybe JPA query is better?
    public boolean overlapExists(LocalDate from, LocalDate to, Integer id) {
        Employee employee = employeeRepository.getReferenceById(id);
        List<Vacation> vacations = employee.getVacations();
        for(Vacation vacation : vacations){
            if(from.isEqual(vacation.getFrom())) return true;
            if(dateIsBetween(from, vacation.getFrom(), vacation.getTo())) return true;
            if(dateIsBetween(to, vacation.getFrom(), vacation.getTo())) return true;
            if(dateIsBetween(vacation.getFrom(),from,to)) return true;
            if(dateIsBetween(vacation.getTo(),from, to)) return true;
        }

        return false;
    }

    private boolean dateIsBetween(LocalDate dateToCheck, LocalDate first, LocalDate second){
        if(dateToCheck.isAfter(first) && dateToCheck.isBefore(second)) return true;
        return false;
    }

    public boolean notEnoughFreeDays(VacationCommand vacationCommand) {
        Employee employee = employeeRepository.getReferenceById(vacationCommand.getEmployeeId());
        Vacation vacation = vacationMapper.toVacationFromCommand(vacationCommand);

        float daysOff = vacation.getDaysOff();

        if(daysOff > employee.getVacationDaysAvailable()) return true;
        return false;
    }
}
