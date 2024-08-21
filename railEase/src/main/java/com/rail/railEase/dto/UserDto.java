package com.rail.railEase.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int userId;
    private String username;

    private String password;

    private String email;
    private String phone;

    private LocalDateTime createdAt;

    private Long balance;

 }
