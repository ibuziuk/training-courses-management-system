package org.exadel.training.utils;

import org.exadel.training.model.Role;

import java.util.Set;

public final class RoleUtil {
    public static final String ADMIN_SMALL = "administrator";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_EXTERNAL = "ROLE_EXTERNAL";
    private static final String ROLE = "ROLE";
    private static final String UNDERLINE = "_";
    private static final String ADMINISTRATOR = "Administrator";
    private static final String USER = "User";
    private static final String EXTERNAL = "External";
    private static final String ADMIN_BIG = "ADMIN";
    private static final String EXTERNAL_SMALL = "external";

    public static String buildRoleForAuthorization(String role) {
        StringBuilder sb = new StringBuilder(ROLE);
        sb.append(UNDERLINE);
        if (role.equals(ADMIN_SMALL)) {
            sb.append(ADMIN_BIG);
        } else {
            sb.append(role.toUpperCase());
        }
        return sb.toString();
    }

    public static String buildRoleForView(Set<Role> roles) {
        for (Role role : roles) {
            if (role.getRole().equals(ADMIN_SMALL)) {
                return ADMINISTRATOR;
            }
            if (role.getRole().equals(EXTERNAL_SMALL)) {
                return EXTERNAL;
            }
        }
        return USER;
    }
}