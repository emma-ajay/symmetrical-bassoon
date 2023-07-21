package com.AYCTechnologies.yinkas_blog.PostCover;

import com.AYCTechnologies.yinkas_blog.Exceptions.BadRequestException;
import com.AYCTechnologies.yinkas_blog.Image.ImageService;
import com.AYCTechnologies.yinkas_blog.Post.Post;
import com.AYCTechnologies.yinkas_blog.Post.PostRepository;
import com.AYCTechnologies.yinkas_blog.Response.PagedResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class PostCoverService {

    @Autowired
    PostCoverRepository postCoverRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ModelMapper modelMapper;


    public Post getMainPost() {
        Post post = postRepository.findMainPost(Boolean.TRUE);
        if (Objects.isNull(post)) throw new BadRequestException("Error finding main post");
        return post;
    }

    public PostCover getMainPostCover() {
        Post post = getMainPost();
        Long postId = post.getPostId();
        PostCover postCover = postCoverRepository.findByPostId(postId);
        if (Objects.isNull(postCover)) throw new BadRequestException("Error finding main post");
        return postCover;

    }

    public PostCover publish(PostCoverForm model, String userName, String thumbnailUrl,Long postId) {
        PostCover postCover = new PostCover();
        postCover = modelMapper.map(model, PostCover.class);
        postCover.setThumbnailUrl(thumbnailUrl);
        postCover.setPostId(postId);
        return postCoverRepository.save(postCover);
    }

    public PagedResponse<?> postCoverList(int page, int size, String sort) {
        if (Objects.equals(sort, "DESC")) {
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "published_date");
            Page<PostCover> postCover = postCoverRepository.findAllPostCover(pageable);
            if (postCover.getTotalElements() == 0) {
                return new PagedResponse<>(Collections.emptyList(), postCover.getNumber(),
                        postCover.getSize(), postCover.getTotalElements(), postCover.getTotalPages(), postCover.isLast());
            }
            List<PostCover> postCovers = postCover.toList();

            return new PagedResponse<>(postCovers, postCover.getNumber(),
                    postCover.getSize(), postCover.getTotalElements(), postCover.getTotalPages(), postCover.isLast());
        } else {
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "published_date");
            Page<PostCover> postCover = postCoverRepository.findAllPostCover(pageable);
            if (postCover.getTotalElements() == 0) {
                return new PagedResponse<>(Collections.emptyList(), postCover.getNumber(),
                        postCover.getSize(), postCover.getTotalElements(), postCover.getTotalPages(), postCover.isLast());
            }
            List<PostCover> postCovers = postCover.toList();

            return new PagedResponse<>(postCovers, postCover.getNumber(),
                    postCover.getSize(), postCover.getTotalElements(), postCover.getTotalPages(), postCover.isLast());

        }
    }

    public PagedResponse<?> postCoverListByCategory(int page, int size, String sort, String category) {
        if (Objects.equals(sort, "DESC")) {
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "publishedDate");
            Page<PostCover> postCover = postCoverRepository.findPostCoverByCategory(pageable,category);
            if (postCover.getTotalElements() == 0) {
                return new PagedResponse<>(Collections.emptyList(), postCover.getNumber(),
                        postCover.getSize(), postCover.getTotalElements(), postCover.getTotalPages(), postCover.isLast());
            }
            List<PostCover> postCovers = postCover.toList();

            return new PagedResponse<>(postCovers, postCover.getNumber(),
                    postCover.getSize(), postCover.getTotalElements(), postCover.getTotalPages(), postCover.isLast());
        } else {
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "publishedDate");
            Page<PostCover> postCover = postCoverRepository.findPostCoverByCategory(pageable,category);
            if (postCover.getTotalElements() == 0) {
                return new PagedResponse<>(Collections.emptyList(), postCover.getNumber(),
                        postCover.getSize(), postCover.getTotalElements(), postCover.getTotalPages(), postCover.isLast());
            }
            List<PostCover> postCovers = postCover.toList();

            return new PagedResponse<>(postCovers, postCover.getNumber(),
                    postCover.getSize(), postCover.getTotalElements(), postCover.getTotalPages(), postCover.isLast());

        }
    }
}

