package com.AYCTechnologies.yinkas_blog.Post;

import com.AYCTechnologies.yinkas_blog.Draft.DraftService;
import com.AYCTechnologies.yinkas_blog.Html.HtmlService;
import com.AYCTechnologies.yinkas_blog.PostCover.PostCover;
import com.AYCTechnologies.yinkas_blog.Response.ApiResponse;
import com.AYCTechnologies.yinkas_blog.Security.CurrentUser;
import com.AYCTechnologies.yinkas_blog.Security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("api/v1/post")
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    HtmlService pdfService;

    @Autowired
    DraftService draftService;

    @PostMapping(path = "")
    public ResponseEntity<?> uploadPost(@RequestBody CreatePostDTO  model , @CurrentUser CustomUserDetails currentUser, @RequestParam Long draftId ){
        if(Objects.nonNull(draftId)){
            draftService.publishDraft(draftId);
        }
        String userName = currentUser.getName();
        Long userId  = currentUser.getUserId();
        Post post = postService.createPost(model,userName,userId);
        return ResponseEntity.ok(new ApiResponse(true,"New Post Created",post));
    }

    @GetMapping(path = "/main")
    public ResponseEntity<?> getMainPost(){
        Post post = postService.getMainPost();
        return ResponseEntity.ok(new ApiResponse(true,"Main Post",post));
    }



    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getPostCoverById(@PathVariable Long id){
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(new ApiResponse(true,"Post",post));
    }

    @PutMapping("/{id}/main")
    public ResponseEntity<?> updateMainPost(@PathVariable Long id, @CurrentUser CustomUserDetails currentUser){
        Post post = postService.updateMainPost(id);
        return ResponseEntity.ok(new ApiResponse(true,"Updated Main post",post));
    }




}
