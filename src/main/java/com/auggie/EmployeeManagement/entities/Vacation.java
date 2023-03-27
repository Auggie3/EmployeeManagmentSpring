package com.auggie.EmployeeManagement.entities;

import com.auggie.EmployeeManagement.entities.composite_primary_keys.VacationCompositeKey;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.internal.LoadingCache;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "vacation")
@IdClass(VacationCompositeKey.class)
@ToString
public class Vacation {
    @Id
    @Column(name = "employee_id")
    @ToString.Exclude
    private Integer employeeId;

    @Id
    @Temporal(TemporalType.DATE)
    @Column(name = "from_date")
    private LocalDate from;

    @Temporal(TemporalType.DATE)
    @Column(name = "to_date")
    private LocalDate to;

    private float daysOff;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacation vacation = (Vacation) o;
        return employeeId.equals(vacation.employeeId) && from.isEqual(vacation.from);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, from);
    }
}
