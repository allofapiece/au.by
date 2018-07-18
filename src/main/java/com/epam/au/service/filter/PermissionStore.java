package com.epam.au.service.filter;

import com.epam.au.entity.Role;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PermissionStore {
    public List<Role> getAuthorizedRoles(String route) {
        switch (route) {
            case "connect-account":
                return new LinkedList<>(Arrays.asList(Role.USER));

            default:
                return new LinkedList<>(Arrays.asList(Role.ANONYMOUS, Role.USER, Role.SOLVENT, Role.ADMIN));
        }
    }
}
