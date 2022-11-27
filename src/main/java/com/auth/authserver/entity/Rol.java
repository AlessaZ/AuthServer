package com.auth.authserver.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrol", nullable = false)
    private Integer id;

    @Column(name = "rol", nullable = false, length = 45)
    private String nombrerol;

    @ManyToMany
    @JoinTable(name = "resources_has_rol",
            joinColumns = @JoinColumn(name = "rol_idrol"),
            inverseJoinColumns = @JoinColumn(name = "resources_idresources"))
    private List<Resource> resources = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "users_has_rol",
            joinColumns = @JoinColumn(name = "rol_idrol"),
            inverseJoinColumns = @JoinColumn(name = "users_uid"))
    private List<User> users = new ArrayList<>();


    public String getStrResources(){
        List<String> list = new java.util.ArrayList<>(resources.stream().map(Resource::getAlias).toList());
        list.add("Acceso a internet");
        return String.join(", ", list);
    }

}