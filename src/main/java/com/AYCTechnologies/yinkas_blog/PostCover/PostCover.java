package com.AYCTechnologies.yinkas_blog.PostCover;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table()
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostCover {
    @Id
    @SequenceGenerator(name = "post_cover_sequence",
            sequenceName = "post_cover_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "post_cover_sequence"
    )

    private Long postCoverId;
    private Long postId;
    @Column(columnDefinition="text")
    private String blurb;
    @Column(columnDefinition="text")
    private String title;
    private String thumbnailUrl;
    @Column(columnDefinition="text")
    private String description;
    private String publishedDate;
    private String category;
}
