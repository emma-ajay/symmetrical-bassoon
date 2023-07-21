package com.AYCTechnologies.yinkas_blog.Html;

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
public class Html {
    @Id
    @SequenceGenerator(name = "html_sequence",
            sequenceName = "html_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "html_sequence"
    )

    private Long htmId;

    private String fileUrl;

    private String createdBy;

    private String createdDate;

    private Long postId;

    private Long draftId;


    public Html(String fileUrl, String createdBy, String createdDate) {
        this.fileUrl = fileUrl;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }
}
