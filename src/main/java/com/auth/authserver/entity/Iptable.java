package com.auth.authserver.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "iptable")
public class Iptable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idiptable", nullable = false)
    private Integer id;

    @Column(name = "dni")
    private String ip;

    @Column(name = "uid")
    private String uid;
}
