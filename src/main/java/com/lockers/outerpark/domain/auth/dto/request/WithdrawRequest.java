package com.lockers.outerpark.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawRequest {
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;
}
