package com.paw.tgt.user.repository;


import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface CustomizedUserRepository {
    List<User> findByUsernameCustom(String username);

    List<User> findByUsernameJdbc(String username);
}
