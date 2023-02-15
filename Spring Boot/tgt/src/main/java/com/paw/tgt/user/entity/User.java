package com.paw.tgt.user.entity;

import com.paw.tgt.board.entity.Board;
import com.paw.tgt.dog.Dog;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idx;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String addr;
    private Date birth;

    @CreationTimestamp
    private LocalDate regDate;

    @UpdateTimestamp
    private LocalDate memModDate;
    //권한[ROLE_USER, ROLE_ADMIN]
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy="user")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy="user")
    private List<Dog> dogs = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}