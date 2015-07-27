package org.exadel.training.utils;

import org.exadel.training.model.Role;

import java.util.Set;

public final class RoleUtil {
    private static final String ROLE = "ROLE";
    private static final String UNDERLINE = "_";
    private static final String ADMINISTRATOR = "Administrator";
    private static final String USER = "User";
    private static final String ADMIN_SMALL = "admin";
    private static final String USER_SMALL = "user";

    public static String buildRoleForAuthorization(String role) {
        StringBuilder sb = new StringBuilder(ROLE);
        sb.append(UNDERLINE)
          .append(role.toUpperCase());
        return sb.toString();
    }

    public static String buildRoleForView(Set<Role> roles) {
        for (Role role : roles) {
            if (role.getRole().equals(ADMIN_SMALL)) {
                return ADMINISTRATOR;
            }
        }
        return USER;
    }
}
