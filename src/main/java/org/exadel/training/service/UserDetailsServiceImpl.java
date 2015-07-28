package org.exadel.training.service;

import org.exadel.training.dao.UserDAO;
import org.exadel.training.model.Role;
import org.exadel.training.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.exadel.training.utils.RoleUtil.buildRoleForAuthorization;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userDAO.getUserByLogin(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Wrong login");
        }
        List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Set<Role> roles) {
        Set<GrantedAuthority> authoritySet = new HashSet<>();
        for (Role role : roles) {
            authoritySet.add(new SimpleGrantedAuthority(buildRoleForAuthorization(role.getRole())));
        }
        return new ArrayList<>(authoritySet);
    }
}
