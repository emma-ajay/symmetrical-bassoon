package com.AYCTechnologies.yinkas_blog.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

    @Query("select u from User u where u.email =?1 or u.userName =?1")
    User findByUserNameOrEmail(String UserNameOrEmail);

    User findByUserId(Long userId);
    Boolean existsByEmail(String email);

    Boolean existsByUserName(String userName);

    @Query(value = "select * from users",nativeQuery = true)
    Page<User> getAllUsers(Pageable pageable);

    @Query(value = "select * from users where name ILIKE %:keyword% OR email ILIKE %:keyword% ",nativeQuery = true)
    Page<User> searchUser(@Param("keyword")String keyword, Pageable pageable);
}
