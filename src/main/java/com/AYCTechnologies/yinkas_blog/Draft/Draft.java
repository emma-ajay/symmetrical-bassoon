package com.AYCTechnologies.yinkas_blog.Draft;

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
public class Draft {
    @Id
    @SequenceGenerator(name = "draft_sequence",
            sequenceName = "draft_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "draft_sequence"
    )

    private Long draftId;

    private Long userId;

    private String content;

    private String lastModifiedDate;

    private String createdBy;

    private Boolean isPublished;

    private Boolean isHidden;
}
