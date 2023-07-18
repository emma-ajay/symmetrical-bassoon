package com.AYCTechnologies.yinkas_blog.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostForm {
    private MultipartFile pdf;

    private String createdDate;
}
