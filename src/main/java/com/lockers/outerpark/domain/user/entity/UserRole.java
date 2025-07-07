package com.lockers.outerpark.domain.user.entity;

import com.lockers.outerpark.domain.user.exception.InvalidUserRoleException;

import java.util.Arrays;

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
