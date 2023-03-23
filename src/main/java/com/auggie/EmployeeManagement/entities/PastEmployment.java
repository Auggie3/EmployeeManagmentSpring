package com.auggie.EmployeeManagement.entities;

import com.auggie.EmployeeManagement.entities.composite_primary_keys.PastEmploymentCompositeKey;
import jakarta.persistence.*;
import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "past_employment")
@IdClass(PastEmploymentCompositeKey.class)
@ToString
public class PastEmployment {

    @Id
    @Column(name = "employee_id")
    @ToString.Exclude
    private Integer employeeId;

    @Id
    @Column(name = "company_name")
    private String companyName;


    @Id
    @Temporal(TemporalType.DATE)
    @Column(name = "from_date")
    private Date from;

    @Temporal(TemporalType.DATE)
    @Column(name = "to_date")
    private Date to;


    //TODO: use better date formats
    public static boolean comparingDates(Date a, Date b){
        if(a.getYear()==b.getYear())
            if(a.getMonth()==b.getMonth())
                if(a.getDay()==b.getDay())
                    return true;
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PastEmployment that = (PastEmployment) o;
        return employeeId.equals(that.employeeId) && companyName.equals(that.companyName) && comparingDates(from,that.from);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, companyName, from);
    }
}
