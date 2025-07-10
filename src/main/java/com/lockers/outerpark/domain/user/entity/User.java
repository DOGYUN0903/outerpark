package com.lockers.outerpark.domain.user.entity;

import java.time.LocalDate;

import com.lockers.outerpark.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
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

	public void updateBalance(Long balance) {
		this.balance = balance;
	}
}
