package com.lockers.outerpark.domain.user.entity;


import com.lockers.outerpark.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private LocalDate birth; // 생년월일

    @Column(nullable = false)
    private String password;

    private Long balance; // 보유 잔액

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    public User(String email, String nickname, LocalDate birth, String password, Long balance, UserRole userRole) {
        this.email = email;
        this.nickname = nickname;
        this.birth = birth;
        this.password = password;
        this.balance = balance;
        this.userRole = userRole;
    }
}
