package com.AYCTechnologies.yinkas_blog.Post;

import com.AYCTechnologies.yinkas_blog.Exceptions.BadRequestException;
import com.AYCTechnologies.yinkas_blog.Html.Html;
import com.AYCTechnologies.yinkas_blog.Html.HtmlService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    HtmlService htmlService;

    public Post createPost(CreatePostDTO model, String userName,Long userId){


        Post post = new Post();
        post = modelMapper.map(model, Post.class);
        Html html = htmlService.uploadNewHtml(model.getContent(),userName, model.getCreatedDate(), "post",post.getPostId());
        String htmlUrl = html.getFileUrl();
        post.setUserId(userId);
        post.setContent(htmlUrl);
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
    //    if(Objects.isNull(post)) throw new BadRequestException("Error finding main post");
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


    public Post updateMainPost(Long id) {
        removeMain();
        Post newMainPost = postRepository.findPostByPostId(id);
        newMainPost.setPostId(id);
        newMainPost.setIsMain(Boolean.TRUE);
        Post result = postRepository.save(newMainPost);
        return result;
    }
    public void removeMain(){
        Post post = postRepository.findMainPost();
        Long id = post.getPostId();
        post.setPostId(id);
        post.setIsMain(Boolean.FALSE);
        Post rs = postRepository.save(post);
    }
}



