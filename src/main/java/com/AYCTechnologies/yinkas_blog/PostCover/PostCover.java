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
    @SequenceGenerator(name = "post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "post_sequence"
    )

    private Long postCoverId;
    private Long postId;
    private String blurb;
    private String title;
    private String thumbnailUrl;
    private String publishedDate;
    private String category;
}
