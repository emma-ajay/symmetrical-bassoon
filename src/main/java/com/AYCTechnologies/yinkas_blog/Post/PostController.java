package com.AYCTechnologies.yinkas_blog.Post;

import com.AYCTechnologies.yinkas_blog.Exceptions.BadRequestException;
import com.AYCTechnologies.yinkas_blog.Pdf.Pdf;
import com.AYCTechnologies.yinkas_blog.Pdf.PdfService;
import com.AYCTechnologies.yinkas_blog.Response.ApiResponse;
import com.AYCTechnologies.yinkas_blog.Security.CurrentUser;
import com.AYCTechnologies.yinkas_blog.Security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping("api/v1/post")
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    PdfService pdfService;

    @PostMapping(path = "")
    public ResponseEntity<?> uploadPost(@RequestBody CreatePostDTO  model , @CurrentUser CustomUserDetails currentUser){
        String userName = currentUser.getName();
        Post post = postService.createPost(model,userName);
        return ResponseEntity.ok(new ApiResponse(true,"New Post Created",post));
    }

    @GetMapping(path = "/main")
    public ResponseEntity<?> getMainPost(){
        Post post = postService.getMainPost();
        return ResponseEntity.ok(new ApiResponse(true,"New Post Created",post));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id){
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(new ApiResponse(true,"Post",post));
    }




}
