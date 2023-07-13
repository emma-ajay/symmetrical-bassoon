package com.AYCTechnologies.yinkas_blog.Pdf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdfRepository extends JpaRepository<Pdf,Long> {
}
