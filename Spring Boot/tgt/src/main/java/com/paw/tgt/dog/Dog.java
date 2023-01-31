package com.paw.tgt.dog;

import com.paw.tgt.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idx;

    private String name;

    private Float weight;

    private String mbti;

    private String info;

    private String breed;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="dog")
    private User user;
}
