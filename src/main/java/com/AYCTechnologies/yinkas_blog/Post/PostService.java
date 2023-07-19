package com.AYCTechnologies.yinkas_blog.Post;

import com.AYCTechnologies.yinkas_blog.Exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;


    public Post createPost(String pdfUrl,String userName, String createDate){

        Post post = new Post();
        post.setCreatedBy(userName);
        post.setFileUrl(pdfUrl);
        post.setIsDeleted(Boolean.FALSE);
        post.setIsHidden(Boolean.FALSE);
        post.setCreatedDate(createDate);
        post.setIsSaved(Boolean.TRUE);
        post.setIsPublished(Boolean.FALSE);
        post.setIsMain(Boolean.TRUE);

        return postRepository.save(post);

    }

    public Post getMainPost() {
        Post post = postRepository.findMainPost();
        if(Objects.isNull(post)) throw new BadRequestException("Error finding main post");
        return post;
    }


}
