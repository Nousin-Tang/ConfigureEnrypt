package com.nousin.configure.encryption;

import lombok.Data;

import javax.persistence.*;

/**
 * TODO
 *
 * @author Nousin
 * @since 2020/7/5
 */
@Entity
@Table(name = "tb_user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", unique = true, nullable = false, length = 64)
    private String username;

    @Column(name = "password", nullable = false, length = 64)
    private String password;

    @Column(name = "email", length = 64)
    private String email;

}

