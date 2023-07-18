package com.AYCTechnologies.yinkas_blog.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(RoleName roleName);

    @Query("select u from Role u  where u.name = ?1")
    Role findRole (RoleName roleName);
}
