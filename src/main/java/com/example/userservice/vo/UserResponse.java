package com.example.userservice.vo;


import lombok.Data;

import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private String userId;
    private String email;
    private String name;

    private List<OrderResponse> orders;
}
