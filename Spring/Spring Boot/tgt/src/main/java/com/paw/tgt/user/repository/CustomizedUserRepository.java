package com.paw.tgt.user.repository;



import com.paw.tgt.user.entity.User;

import java.util.List;

public interface CustomizedUserRepository {
//    List<Users> findByUsernameCustom(String username);

    List<User> findByUsernameJdbc(String username);
}
