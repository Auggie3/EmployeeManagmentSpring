package com.auggie.EmployeeManagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "role")
@ToString
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;

    @ToString.Exclude
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<Employee> employees = new HashSet<>();


    public void addEmployee(Employee e){
        if(e != null){
            this.getEmployees().add(e);
        }
    }
}
