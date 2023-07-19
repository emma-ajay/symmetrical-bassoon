package com.AYCTechnologies.yinkas_blog.PostCover;

import com.AYCTechnologies.yinkas_blog.Exceptions.BadRequestException;
import com.AYCTechnologies.yinkas_blog.Post.Post;
import com.AYCTechnologies.yinkas_blog.Post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PostCoverService {

    @Autowired
    PostCoverRepository postCoverRepository;

    @Autowired
    PostRepository postRepository;

    public Post createPostCover(String pdfUrl,String userName, String createDate){

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

    public PostCover getMainPostCover(){
        Post post = getMainPost();
        Long postId = post.getPostId();
        PostCover postCover = postCoverRepository.findByPostId(postId);
        if(Objects.isNull(postCover)) throw new BadRequestException("Error finding main post");
        return postCover;

    }
}
