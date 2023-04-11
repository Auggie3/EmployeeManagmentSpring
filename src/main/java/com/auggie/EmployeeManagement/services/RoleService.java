package com.auggie.EmployeeManagement.services;

import com.auggie.EmployeeManagement.dto.command.RoleCreateCommand;
import com.auggie.EmployeeManagement.dto.query.RoleQuery;
import com.auggie.EmployeeManagement.entities.Role;
import com.auggie.EmployeeManagement.mappers.RoleMapper;
import com.auggie.EmployeeManagement.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    public Set<RoleQuery> findAll() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleQuery)
                .collect(Collectors.toSet());
    }

    public void createRole(RoleCreateCommand roleCreateCommand) {
        Role role = roleMapper.toRole(roleCreateCommand);
        roleRepository.save(role);
    }

    public void deleteRole(Integer id) {
        roleRepository.deleteById(id);
    }
}
