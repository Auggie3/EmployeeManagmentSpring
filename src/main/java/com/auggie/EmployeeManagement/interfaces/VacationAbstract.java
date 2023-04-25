package com.auggie.EmployeeManagement.interfaces;

import com.auggie.EmployeeManagement.entities.composite_primary_keys.VacationCompositeKey;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
@IdClass(VacationCompositeKey.class)
@ToString
@EqualsAndHashCode
@MappedSuperclass
public abstract class VacationAbstract {
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


    public final float getDaysOff(){
        LocalDate from = getFrom();
        LocalDate to = getTo();

        if(to == null) return isPartOfWeekend(from)? 0 : 1;

        float days = 0.0f;

        while(from.isBefore(to)){
            if(!isPartOfWeekend(from)){
                days++;
            }

            from = from.plus(1, ChronoUnit.DAYS);
        }

        return days;
    }

    private final boolean isPartOfWeekend(LocalDate date){
        if(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)
            return true;

        return false;
    }

}
