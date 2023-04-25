package com.auggie.EmployeeManagement.entities;

import com.auggie.EmployeeManagement.interfaces.VacationAbstract;
import jakarta.persistence.*;

@Entity
@Table(name = "vacation_request")
public class VacationRequest extends VacationAbstract {

}

