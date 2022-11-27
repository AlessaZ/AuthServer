package com.auth.authserver.Repository;

import com.auth.authserver.entity.Resource;
import com.auth.authserver.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ResourceRepo extends JpaRepository<Resource, Integer> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from resources_has_rol rh where rh.resources_idresources = :id")
    void deleteAllRhById(int id);

}
