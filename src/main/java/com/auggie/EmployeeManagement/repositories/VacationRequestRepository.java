package com.auggie.EmployeeManagement.repositories;

import com.auggie.EmployeeManagement.entities.VacationRequest;
import com.auggie.EmployeeManagement.entities.composite_primary_keys.VacationCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacationRequestRepository extends JpaRepository<VacationRequest, VacationCompositeKey> {

}
