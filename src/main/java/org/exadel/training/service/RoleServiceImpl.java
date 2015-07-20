package org.exadel.training.service;

import org.exadel.training.dao.RoleDAO;
import org.exadel.training.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDAO roleDAO;

    @Override
    @Transactional
    public  void addRole(Role role) {
        roleDAO.addRole(role);
    }

    @Override
    @Transactional
    public void removeRole(Role role) {
        roleDAO.removeRole(role);
    }
}
