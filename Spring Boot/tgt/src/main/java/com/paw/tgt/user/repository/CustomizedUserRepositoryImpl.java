package com.paw.tgt.user.repository;


import com.paw.tgt.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CustomizedUserRepositoryImpl  implements CustomizedUserRepository {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    JdbcTemplate jdbcTemplate;

//    @Override
//    public List<User> findByUsernameCustom(String username) {
//        QUser qUser = QUser.user;
//        JPAQuery<?> query = new JPAQuery<Void>(em);
//        List<User> users = query.select(qUser)
//                .from(qUser)
//                .where(qUser.username.contains(username))
//                .fetch();
//        return users;
//    }

    @Override
    public List<User> findByUsernameJdbc(String username) {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM USER WHERE username like ?",
                new Object[]{"%" + username + "%"},
                new BeanPropertyRowMapper(User.class));
        return users;
    }
}