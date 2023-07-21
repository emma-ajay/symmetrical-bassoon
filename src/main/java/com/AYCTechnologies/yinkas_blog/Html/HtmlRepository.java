package com.AYCTechnologies.yinkas_blog.Html;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HtmlRepository extends JpaRepository<Html,Long> {
}
