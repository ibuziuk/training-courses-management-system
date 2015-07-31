package org.exadel.training.service;


import org.exadel.training.model.Role;

public interface RoleService {
    Role getRole(String name);

    void addRole(Role role);

    void removeRole(Role role);
}