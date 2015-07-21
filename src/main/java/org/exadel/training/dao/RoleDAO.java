package org.exadel.training.dao;

import org.exadel.training.model.Role;

import java.util.List;

public interface RoleDAO {
    void addRole(Role role);

    void removeRole(Role role);
}
