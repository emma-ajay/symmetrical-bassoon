package com.AYCTechnologies.yinkas_blog.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreatePostDTO {
    private String content;

    private String createdDate;
}
