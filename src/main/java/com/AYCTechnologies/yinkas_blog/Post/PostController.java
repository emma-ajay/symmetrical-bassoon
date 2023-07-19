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
@RequestMapping("api/vi/post")
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    PdfService pdfService;

    @PostMapping(path = "" ,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadPost(@ModelAttribute PostForm model , @CurrentUser CustomUserDetails currentUser){
        if(Objects.isNull(model)) throw new BadRequestException("Include a pdf");
        String userName = currentUser.getName();
        Pdf pdf = pdfService.uploadNewPdf(model.getPdf());

        Post post = postService.createPost(pdf.getFileUrl(),userName, model.getCreatedDate());

        return ResponseEntity.ok(new ApiResponse(true,"New Post Created",post));
    }

    @GetMapping(path = "/main")
    public ResponseEntity<?> getMainPost(){

        return ResponseEntity.ok(new ApiResponse(true,"New Post Created",""));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getPostById(){
        
        return ResponseEntity.ok(new ApiResponse(true,"Post"));
    }




}
