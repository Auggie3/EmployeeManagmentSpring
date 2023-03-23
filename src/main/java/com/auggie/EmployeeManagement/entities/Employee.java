package com.auggie.EmployeeManagement.entities;

import jakarta.persistence.*;
import lombok.*;

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
    private Date startDate;

    private String position;

    private float vacationDaysPerYear = 22f;

    private float vacationDaysAvailable = 22f;


    @OneToMany(mappedBy = "employeeId", orphanRemoval = true, fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<Vacation> vacations = new ArrayList<>();


    @OneToMany(mappedBy = "employeeId", orphanRemoval = true, fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<PastEmployment> pastEmployments = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "employee_role",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();


    //TODO: Make better autentification and authorization

    private String username;
    private String password;


    public void addRole(Role role){
        if(role != null){
            this.getRoles().add(role);
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
}
