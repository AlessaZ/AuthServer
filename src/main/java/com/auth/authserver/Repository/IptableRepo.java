package com.auth.authserver.Repository;

import com.auth.authserver.entity.Iptable;
import com.auth.authserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IptableRepo extends JpaRepository<Iptable, String> {
}
