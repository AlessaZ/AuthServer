package com.auth.authserver.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @Column(name = "uid", nullable = false, length = 8)
    private String uid;

    @Column(name = "codigo", nullable = false, length = 8)
    private String codigo;

    @Column(name = "password", nullable = false, length = 64)
    private String password;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;
}