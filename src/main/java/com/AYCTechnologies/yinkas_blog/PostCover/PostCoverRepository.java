package com.AYCTechnologies.yinkas_blog.PostCover;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCoverRepository extends JpaRepository<PostCover,Long> {
}
