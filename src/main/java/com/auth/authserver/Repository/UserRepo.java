package com.auth.authserver.Repository;

import com.auth.authserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {

    public User findUserByCodigoAndPassword(String codigo, String password);
}
