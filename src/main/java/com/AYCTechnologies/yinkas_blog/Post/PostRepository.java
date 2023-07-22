package com.AYCTechnologies.yinkas_blog.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    @Query(value = "select * from posts where is_main=true",nativeQuery = true)
    Post findMainPost();

    @Query(value = "select u from Post u where u.postId =?1")
    Post findPostByPostId(Long id);
}
