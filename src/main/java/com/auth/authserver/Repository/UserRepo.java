package com.auth.authserver.Repository;

import com.auth.authserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, String> {

    public User findUserByCodigoAndPassword(String codigo, String password);

    @Query(nativeQuery = true, value = "select case when exists(select * from users_has_rol "
            + "where users_uid = :uid and rol_idrol = 1) then 'true' else 'false' end from dual")
    public Boolean isUserDTI(String uid);

    @Query(nativeQuery = true, value = "select case when exists(select * from iptable ipt\n" +
            "inner join users_has_rol ur on ipt.uid = ur.users_uid\n" +
            "inner join resources_has_rol rr on rr.rol_idrol = ur.rol_idrol \n" +
            "inner join resources rs on rs.idresources = rr.resources_idresources\n" +
            "where ipt.ip = :userIp " +
            "and rs.ip = :resIp and rs.port = :resPort)\n" +
            "then 'true' else 'false' end \n" +
            "as 'forwarding' from dual")
    public Boolean hasAccessToResource(String userIp, String resIp, String resPort);
}
