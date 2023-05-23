package com.example.userservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(unique = true, length = 50, nullable = false)
    private String email;

    @Column(length = 50, nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String encryptedPassword;

}
