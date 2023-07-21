package com.AYCTechnologies.yinkas_blog.PostCover;

import com.AYCTechnologies.yinkas_blog.Config.AppConstants;
import com.AYCTechnologies.yinkas_blog.Exceptions.BadRequestException;
import com.AYCTechnologies.yinkas_blog.Image.Image;
import com.AYCTechnologies.yinkas_blog.Image.ImageService;
import com.AYCTechnologies.yinkas_blog.Post.CreatePostDTO;
import com.AYCTechnologies.yinkas_blog.Post.Post;
import com.AYCTechnologies.yinkas_blog.Post.PostService;
import com.AYCTechnologies.yinkas_blog.Response.ApiResponse;
import com.AYCTechnologies.yinkas_blog.Response.PagedResponse;
import com.AYCTechnologies.yinkas_blog.Security.CurrentUser;
import com.AYCTechnologies.yinkas_blog.Security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/publish")
public class PostCoverController {
    @Autowired
    PostCoverService postCoverService;

    @Autowired
    PostService postService;

    @Autowired
    ImageService imageService;

    @PostMapping(path = "/{postId}/post", consumes = "multipart/form-data")
    public ResponseEntity<?> publishPost(@ModelAttribute PostCoverForm model, @PathVariable Long postId, @CurrentUser CustomUserDetails currentUser) {
//        if(Objects.isNull(model.getThumbnail())) throw new BadRequestException("Include a thumbnail");
        String userName = currentUser.getName();
        Image image = imageService.uploadNewImage(model.getThumbnail());
        PostCover post = postCoverService.publish(model, userName, image.getImageUrl(), postId);
        postService.updatePublishedStatus(postId, model.getPublishedDate());
        return ResponseEntity.ok(new ApiResponse(true, "New Post Created", post));
    }


    @GetMapping("")
    public PagedResponse<?> getAllPostsCover(@RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                             @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
                                             @RequestParam(name = "sort", defaultValue = "DESC") String sort) {

        return postCoverService.postCoverList(page, size, sort);
    }

    @GetMapping(value = "/{category}/category")
    public PagedResponse<?> getAllPostsCategory(@RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                             @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
                                             @RequestParam(name = "sort", defaultValue = "DESC") String sort, @PathVariable String category) {

        return postCoverService.postCoverListByCategory(page, size, sort,category);
    }
}



