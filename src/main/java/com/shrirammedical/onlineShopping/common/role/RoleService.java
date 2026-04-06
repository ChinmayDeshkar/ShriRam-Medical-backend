package com.shrirammedical.onlineShopping.common.role;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


// Helper service to fetch role details by name or id, used in authentication and authorization processes
@Slf4j
@Service
public class RoleService {

    @Autowired
    RoleRepo roleRepo;

    // Fetches the role ID corresponding to a given role name, throws an exception if the role is not found
    public Long getRoleIdByName(String roleName) {
        log.info("Fetching role ID for role name: {}", roleName);
        return roleRepo.findByRoleName(roleName).orElseThrow().getRoleId();
    }

    // Fetches the role name corresponding to a given role ID, throws an exception if the role is not found
    public String getRoleNameById(Long roleId) {
        return roleRepo.findById(roleId).orElseThrow().getRoleName();
    }

}
