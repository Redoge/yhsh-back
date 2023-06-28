package app.redoge.yhshback.entity.enums;

import app.redoge.yhshback.exception.NotFoundException;

public enum UserRole {
    ADMIN, USER;

    public static UserRole findByName(String name) throws NotFoundException {
        for (UserRole role : UserRole.values()) {
            if (role.toString().equalsIgnoreCase(name)) {
                return role;
            }
        }
        throw new NotFoundException("User role", name);
    }
}
