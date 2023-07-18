package com.AYCTechnologies.yinkas_blog.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
