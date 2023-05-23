package com.example.userservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    /**
     * 사용자의 회원가입 요청에 대한 JSON 객체를 받기 위한 Value Object
     */

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String name;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 40, message = "Password must be between 6 and 40 characters")
    private String password;

    @NotBlank(message = "Email cannot be blank")
    @Size(max = 50, message = "Email must be no more than 50 characters")
    @Email(message = "Email must be a valid email address")
    private String email;

    public boolean isValid(){
        return name != null && !name.isEmpty()
                && password != null && !password.isEmpty()
                && email != null && !email.isEmpty();
    }
}
