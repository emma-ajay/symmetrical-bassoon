package com.AYCTechnologies.yinkas_blog.Post;

import com.AYCTechnologies.yinkas_blog.Exceptions.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ModelMapper modelMapper;

    public Post createPost(CreatePostDTO model, String userName){

        Post post = new Post();
        post = modelMapper.map(model, Post.class);
        post.setCreatedBy(userName);
        post.setIsDeleted(Boolean.FALSE);
        post.setIsHidden(Boolean.FALSE);
        post.setIsSaved(Boolean.TRUE);
        post.setIsPublished(Boolean.FALSE);
        post.setIsMain(Boolean.FALSE);
        List<Post> postList = postRepository.findAll();
        if(postList.size()==0) post.setIsMain(Boolean.TRUE);

        return postRepository.save(post);

    }

    public Post getMainPost() {
        Post post = postRepository.findMainPost();
        if(Objects.isNull(post)) throw new BadRequestException("Error finding main post");
        return post;
    }


    public Post getPostById(Long id) {
        Post post = postRepository.findPostByPostId(id);
        if(Objects.isNull(post)) throw new BadRequestException("Error finding post");
        return post;
    }

    public void  updatePublishedStatus (Long postId, String publishedDate){
        Post post = getPostById(postId);
        post.setPostId(postId);
        post.setIsPublished(Boolean.TRUE);
        post.setPublishedDate(publishedDate);
        Post rs = postRepository.save(post);
    }
}
