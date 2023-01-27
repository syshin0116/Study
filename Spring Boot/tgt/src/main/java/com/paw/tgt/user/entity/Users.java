package com.paw.tgt.user.entity;

import com.paw.tgt.board.entity.Board;
import com.paw.tgt.dog.Dog;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idx;

    private String id;

    private String pw;

    private String phone;

    private String email;

    private String addr;

    private Boolean type;

    private LocalDate regDate;

    private LocalDate memModDate;

    //권한[ROLE_USER, ROLE_ADMIN]
    @Column(name="MEM_ROLE")
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(mappedBy="users")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy="users")
    private List<Dog> dogs = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}