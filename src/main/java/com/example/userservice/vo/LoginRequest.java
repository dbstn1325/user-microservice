package com.example.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {

    @NotNull(message = "이메일은 빈 값이면 안됩니다.")
    @Size(min = 2, message = "이메일은 2글자 이상이여야 합니다.")
    @Email
    private String email;

    @NotNull(message = "패스워드는 빈 값이면 안됩니다.")
    @Size(min = 10, message = "비밀번호를 10자리 이상 입력해주세요.")
    private String password;
}
