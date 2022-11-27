package com.auth.authserver.entity;

import javax.persistence.*;

@Entity
@Table(name = "resources")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idresources", nullable = false)
    private int id;

    @Column(name = "alias", nullable = false, length = 45)
    private String alias;

    @Column(name = "ip", nullable = false, length = 45)
    private String ip;

    @Column(name = "port", nullable = false)
    private Integer port;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

}