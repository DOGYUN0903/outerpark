package com.lockers.outerpark.domain.user.entity;

import java.util.Arrays;

import static com.lockers.outerpark.domain.user.exception.UserException.*;

public enum UserRole {
    USER,
    ADMIN;

    public static UserRole of(String role) {
        return Arrays.stream(UserRole.values())
                .filter(r -> r.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(InvalidUserRoleException::new);
    }
}
