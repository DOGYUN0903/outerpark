package com.lockers.outerpark.domain.auth.dto.request;

import java.time.LocalDate;

import com.lockers.outerpark.domain.user.entity.User;
import com.lockers.outerpark.domain.user.type.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
	@Email(message = "올바른 이메일 형식이어야 합니다.")
	@NotBlank(message = "이메일은 필수 입력값입니다.")
	private String email;

	@NotBlank(message = "닉네임은 필수 입력값입니다.")
	@Size(min = 2, max = 20, message = "닉네임은 2자 이상 20자 이하로 입력해주세요.")
	private String nickname;

	@NotNull(message = "생년월일은 필수 입력값입니다.")
	@Past(message = "생년월일은 과거 날짜여야 합니다.")
	private LocalDate birth;

	@NotBlank(message = "비밀번호는 필수 입력값입니다.")
	@Pattern(
		regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+\\[{\\]};:'\",<.>/?]).{8,}$",
		message = "비밀번호는 최소 8글자 이상, 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함해야합니다."
	)
	private String password;

	public User toEntity(String encodedPassword) {
		return new User(
			this.getEmail(),
			this.getNickname(),
			this.getBirth(),
			encodedPassword,
			100000L,
			UserRole.USER
		);
	}
}
