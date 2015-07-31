package org.exadel.training.dao;

import org.exadel.training.model.Role;

public interface RoleDAO {
    Role getRoleByName(String name);

    void addRole(Role role);

    void removeRole(Role role);
}
