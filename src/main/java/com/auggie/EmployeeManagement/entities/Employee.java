package com.auggie.EmployeeManagement.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "employee")
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    private String position;

    @OneToMany(mappedBy = "employeeId", orphanRemoval = true, fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<Vacation> vacations = new ArrayList<>();

    @OneToMany(mappedBy = "employeeId", orphanRemoval = true, fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<PastEmployment> pastEmployments = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "employee_role",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @Column(unique = true)
    private String username;
    private String password;

// ----OPTIMISTIC LOCKING-----
//    @Version
//    private int version;




    public void addRole(Role role){
        if(role != null){
            this.getRoles().add(role);
        }
    }

    public void removeRole(Role role){
        if(role != null){
            this.getRoles().remove(role);
        }
    }

    public void addVacation(Vacation vacation){
        if(vacation != null){
            this.getVacations().add(vacation);
        }
    }

    public void addPastEmployment(PastEmployment pastEmployment){
        if(pastEmployment != null){
            this.getPastEmployments().add(pastEmployment);
        }
    }

    public void deletePastEmployment(PastEmployment pastEmployment){
        if(pastEmployment != null){
            this.getPastEmployments().remove(pastEmployment);
        }
    }

    public void deleteVacation(Vacation vacation) {
        if(vacation != null){
            this.getVacations().remove(vacation);
        }
    }


    //this is done to keep the roles, vacations and past employments when updating employee
    public void copyEmployee(Employee fromEmployee){
        this.setName(fromEmployee.getName());
        this.setPosition(fromEmployee.getPosition());
        this.setStartDate(fromEmployee.getStartDate());
        this.setUsername(fromEmployee.getUsername());
    }

    public void changePassword(String password){
        String encryptedPassword = new BCryptPasswordEncoder().encode(password);
        setPassword(encryptedPassword);
    }


    public float getVacationDaysPerYear(){
        List<PastEmployment> pastEmployments = getPastEmployments();

        float days = 20.0f;
        float yearsOfWork = 0.0f;

        for(PastEmployment pastEmployment : pastEmployments){
            yearsOfWork += pastEmployment.getYearsOfWork();
        }

        int addedDays = (int)yearsOfWork / 5;

        return days + addedDays;
    }

    public float getVacationDaysAvailable() {
        List<Vacation> vacations = getVacations();
        int currentYear = LocalDate.now().getYear();

        float daysOff = 0.0f;

        for(Vacation vacation : vacations){
            if(vacation.getFrom().getYear() == currentYear)
                daysOff += vacation.getDaysOff();
        }

        return getVacationDaysPerYear() - daysOff;
    }
}
