package com.paw.tgt.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;

    private String password;

    private String phone;

    private String email;

    private String addr;

    private Boolean type;

    private LocalDate regDate;

    private LocalDate memModDate;
}