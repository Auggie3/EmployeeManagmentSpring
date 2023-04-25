package com.auggie.EmployeeManagement.entities;

import com.auggie.EmployeeManagement.entities.composite_primary_keys.VacationCompositeKey;
import com.auggie.EmployeeManagement.interfaces.VacationAbstract;
import jakarta.persistence.*;
import lombok.*;
import org.joda.time.Days;
import org.springframework.cglib.core.internal.LoadingCache;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "vacation")
public class Vacation extends VacationAbstract {

}
