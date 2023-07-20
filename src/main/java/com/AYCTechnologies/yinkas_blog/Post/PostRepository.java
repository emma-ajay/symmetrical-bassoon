package com.AYCTechnologies.yinkas_blog.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    @Query("select u from Post u where u.isMain=true")
    Post findMainPost();

    Post findPostByPostId(Long id);
}
