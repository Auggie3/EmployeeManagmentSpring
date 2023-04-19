package com.auggie.EmployeeManagement.filters;

import com.auggie.EmployeeManagement.entities.Employee;
import com.auggie.EmployeeManagement.entities.Role;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class EmployeeSpecification implements Specification<Employee> {

    private final EmployeeFilter employeeFilter;



    @Override
    public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        filterByName(root, criteriaBuilder, predicates);
        filterByPosition(root, criteriaBuilder, predicates);
        filterByRoles(root, criteriaBuilder, predicates);

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    private void filterByName(Root<Employee> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates){
        String name = employeeFilter.getName();
        if(StringUtils.isNotBlank(name)){
            predicates.add(
                    criteriaBuilder.like(root.get("name"), "%" + name + "%")
            );
        }
    }

    private void filterByPosition(Root<Employee> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates){
        String position = employeeFilter.getPosition();
        if(StringUtils.isNotBlank(position)){
            predicates.add(
                    criteriaBuilder.equal(root.get("position"), position)
            );
        }
    }

    private void filterByRoles(Root<Employee> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates){

        List<String> roles = employeeFilter.getRoles();
        List<Predicate> rolePredicates = new ArrayList<>();

        Join<Employee, Role> roleJoin = root.join("roles");

        if(roles != null && !roles.isEmpty()){
            for (String role: roles) {
                if(StringUtils.isNotBlank(role)){
                    rolePredicates.add(
                            criteriaBuilder.equal(
                                    roleJoin.get("name"),
                                    role
                            )
                    );
                }
            }

            predicates.add(
                    criteriaBuilder.or(rolePredicates.toArray(new Predicate[rolePredicates.size()]))
            );
        }

    }
}
