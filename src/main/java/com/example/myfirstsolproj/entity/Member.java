package com.example.myfirstsolproj.entity;

import com.example.myfirstsolproj.constant.Role;

import jakarta.persistence.*;

import lombok.*;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class Member{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long mno;

    @Column(length = 50, nullable = false, unique = true)
    private String email;
    @Column(length = 10, nullable = false)
    private String name;
    @Column(length = 20, nullable = false, unique = true)
    private String userID;
    @Column(nullable = false)
    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;
}
