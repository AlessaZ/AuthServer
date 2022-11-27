package com.auth.authserver.Repository;

import com.auth.authserver.entity.Iptable;
import com.auth.authserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface IptableRepo extends JpaRepository<Iptable, Integer> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value= "CREATE EVENT delete_ip_:id " +
            "ON SCHEDULE AT CURRENT_TIMESTAMP + INTERVAL :minutos MINUTE " +
            "ON COMPLETION NOT PRESERVE ENABLE " +
            "DO delete from iptable where idiptable=:id")
    void crearEvento(int id, int minutos);

    public Optional<Iptable> findByUidAndIp(String uid, String ip);
}
