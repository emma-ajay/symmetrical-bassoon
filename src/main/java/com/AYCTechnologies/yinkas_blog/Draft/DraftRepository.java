package com.AYCTechnologies.yinkas_blog.Draft;

import com.AYCTechnologies.yinkas_blog.PostCover.PostCover;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DraftRepository extends JpaRepository<Draft,Long> {
    @Query("select u from Draft u")
    Page<Draft> findDrafts(Pageable pageable);


    Draft findDraftByDraftId(Long draftId);

    Page<Draft> findDraftByUserId(Pageable pageable, Long userId);


//    @Query("select u from Draft u where u.isPublished=false AND where u.userId = :userId")
//    Draft findAllDrafts(@Param("userId") String userId);
}
