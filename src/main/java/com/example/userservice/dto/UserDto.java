package com.example.userservice.dto;

import com.example.userservice.vo.OrderResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDto {
    private String id;

    private String userId;

    private String email;
    private String name;
    private String password;
    private String encryptedPassword;
    private LocalDateTime createdAt;


    private List<OrderResponse> orders;
}
