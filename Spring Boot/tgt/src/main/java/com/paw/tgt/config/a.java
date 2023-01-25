package com.paw.tgt.config;


import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;



public class a {
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                );
        // ...
    }
}