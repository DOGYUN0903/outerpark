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
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String nickname;
    private LocalDate birth; // 생년월일
    private String password;
    private Long balance; // 보유 잔액
    @Enumerated(EnumType.STRING)
    private UserRole userRole;


}
