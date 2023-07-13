package com.AYCTechnologies.yinkas_blog.Pdf;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pdf {
    @Id
    @SequenceGenerator(name = "pdf_sequence",
            sequenceName = "pdf_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "pdf_sequence"
    )

    private Long pdfId;

    private String fileUrl;

    private String createdBy;

    private String createdDate;

    public Pdf(String fileUrl, String createdBy, String createdDate) {
        this.fileUrl = fileUrl;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }
}
