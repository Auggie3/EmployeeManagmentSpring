package com.auggie.EmployeeManagement.services;

import com.auggie.EmployeeManagement.configurations.CacheHelper;
import com.auggie.EmployeeManagement.dto.query.VacationQuery;
import com.auggie.EmployeeManagement.dto.query.VacationRequestQuery;
import com.auggie.EmployeeManagement.entities.Employee;
import com.auggie.EmployeeManagement.entities.Vacation;
import com.auggie.EmployeeManagement.entities.VacationRequest;
import com.auggie.EmployeeManagement.entities.composite_primary_keys.VacationCompositeKey;
import com.auggie.EmployeeManagement.mappers.EmployeeMapper;
import com.auggie.EmployeeManagement.mappers.VacationMapper;
import com.auggie.EmployeeManagement.repositories.EmployeeRepository;
import com.auggie.EmployeeManagement.repositories.VacationRequestRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
                    @CacheEvict(value = "employee-details", key = "#vacationQuery.getEmployeeId()")
            }
    )
    public void addVacation(VacationQuery vacationQuery) {
        Vacation vacation = vacationMapper.toVacation(vacationQuery);
        Employee employee = employeeRepository.getReferenceById(vacation.getEmployeeId());

        float daysAvailable = employee.getVacationDaysAvailable();
        daysAvailable-=vacation.getDaysOff();
        employee.setVacationDaysAvailable(daysAvailable);

        employee.addVacation(vacation);
        employeeRepository.save(employee);
    }


    @Caching(
            evict = {
                    @CacheEvict(value = "employees", key = "'all-employees-details'"),
                    @CacheEvict(value = "employee-details", key = "#vacationQuery.getEmployeeId()")
            }
    )
    public void deleteVacation(VacationQuery vacationQuery) {
        Vacation vacation = vacationMapper.toVacation(vacationQuery);
        Employee employee = employeeRepository.getReferenceById(vacation.getEmployeeId());


        float daysAvailable = employee.getVacationDaysAvailable();
        daysAvailable+=vacation.getDaysOff();
        employee.setVacationDaysAvailable(daysAvailable);

        employee.deleteVacation(vacation);
        employeeRepository.save(employee);


    }

    public void requestVacation(VacationQuery vacationQuery) {
        VacationRequest vacationRequest = vacationMapper.toVacationRequest(vacationQuery);
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

    public void allowVacationRequest(VacationQuery vacationQuery) {
        VacationCompositeKey vacationCompositeKey = new VacationCompositeKey();
        vacationCompositeKey.setEmployeeId(vacationQuery.getEmployeeId());
        vacationCompositeKey.setFrom(vacationQuery.getFrom());
        VacationRequest vacationRequest = vacationRequestRepository.findById(vacationCompositeKey)
                .orElseThrow(EntityNotFoundException::new);

        //TODO: maybe i dont need this because i already have vacationQuery but just for safety...
        VacationQuery allowedVacationQuery = vacationMapper.toVacationQueryFromRequest(vacationRequest);

        //CACHE: TODO: u should separate vacation and vacation request services into 2 classes
        cacheHelper.evictAllEntriesFromCache();

        addVacation(allowedVacationQuery);

        vacationRequestRepository.delete(vacationRequest);
    }

    public void denyVacationRequest(VacationQuery vacationQuery) {
        VacationCompositeKey vacationCompositeKey = new VacationCompositeKey();
        vacationCompositeKey.setEmployeeId(vacationQuery.getEmployeeId());
        vacationCompositeKey.setFrom(vacationQuery.getFrom());

        VacationRequest vacationRequest = vacationRequestRepository.getReferenceById(vacationCompositeKey);

        vacationRequestRepository.delete(vacationRequest);
    }

    //TODO: Maybe JPA query is better?
    public boolean overlapExists(LocalDate from, LocalDate to, Integer id) {
        Employee employee = employeeRepository.getReferenceById(id);
        List<Vacation> vacations = employee.getVacations();
        for(Vacation vacation : vacations){
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

    public boolean notEnoughFreeDays(Integer id, float daysOff) {
        Employee employee = employeeRepository.getReferenceById(id);
        if(daysOff > employee.getVacationDaysAvailable()) return true;
        return false;
    }
}
