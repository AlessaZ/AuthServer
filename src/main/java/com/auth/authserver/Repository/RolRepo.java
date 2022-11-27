package com.auth.authserver.Repository;

import com.auth.authserver.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepo extends JpaRepository<Rol, Integer> {
}
