package com.AYCTechnologies.yinkas_blog.Post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Post {
    @Id
    @SequenceGenerator(name = "post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "post_sequence"
    )

    private Long postId;

    private Long userId;

    private String fileUrl;

    private String createdBy;

    private String createdDate;

    private Boolean isSaved;

    private Boolean isPublished;

    private Boolean isHidden;

    private Boolean isDeleted;

    private Boolean isMain;




}
