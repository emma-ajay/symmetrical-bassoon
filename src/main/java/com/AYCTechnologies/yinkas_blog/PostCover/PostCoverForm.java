package com.AYCTechnologies.yinkas_blog.PostCover;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostCoverForm {
    private MultipartFile thumbnail;
    private String blurb;
    private String description;
    private String title;
    private String publishedDate;
    private String category;
}
