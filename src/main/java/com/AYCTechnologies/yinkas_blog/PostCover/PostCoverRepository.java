package com.AYCTechnologies.yinkas_blog.PostCover;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCoverRepository extends JpaRepository<PostCover,Long> {
    @Query(value = "select u from PostCover u where u.postId =?1")
    PostCover findByPostId(Long postId);

    @Query(value = "select * from post_cover",nativeQuery = true)
    Page<PostCover> findAllPostCover(Pageable pageable);

    @Query("select u from PostCover u where lower(u.category) like lower(:category)")
    Page<PostCover> findPostCoverByCategory(Pageable pageable, @Param("category") String category);


    @Query("select u from PostCover u where u.postId =?1")
    PostCover findPostCoverByPostId(Long id);

    @Query("select u from PostCover u where u.userId =?1")
    Page<PostCover> findPostCoverByUserId(Pageable pageable, Long userId);
}
