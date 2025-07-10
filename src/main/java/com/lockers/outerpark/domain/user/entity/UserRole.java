package com.lockers.outerpark.domain.user.entity;

public enum UserRole {
    USER,
    ADMIN;

    // public static UserRole of(String role) {
    //     return Arrays.stream(UserRole.values())
    //             .filter(r -> r.name().equalsIgnoreCase(role))
    //             .findFirst()
    //             .orElseThrow(InvalidUserRoleException::new);
    // }
}
