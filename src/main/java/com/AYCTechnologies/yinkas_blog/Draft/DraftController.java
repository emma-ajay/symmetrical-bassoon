package com.AYCTechnologies.yinkas_blog.Draft;

import com.AYCTechnologies.yinkas_blog.Config.AppConstants;
import com.AYCTechnologies.yinkas_blog.Image.Image;
import com.AYCTechnologies.yinkas_blog.Post.Post;
import com.AYCTechnologies.yinkas_blog.PostCover.PostCover;
import com.AYCTechnologies.yinkas_blog.PostCover.PostCoverForm;
import com.AYCTechnologies.yinkas_blog.Response.ApiResponse;
import com.AYCTechnologies.yinkas_blog.Response.PagedResponse;
import com.AYCTechnologies.yinkas_blog.Security.CurrentUser;
import com.AYCTechnologies.yinkas_blog.Security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/draft")
public class DraftController {
    @Autowired
    DraftService draftService;

    @PostMapping(path = "")
    public ResponseEntity<?> createDraft(@RequestBody DraftDTO model , @CurrentUser CustomUserDetails currentUser) {
        String userName = currentUser.getName();
        Long userId = currentUser.getUserId();
        Draft draft = draftService.createDraft(model, userName, userId);
        return ResponseEntity.ok(new ApiResponse(true, "New Draft Created", draft));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getDraftById(@PathVariable Long id){
        Draft draft = draftService.getDraftById(id);
        return ResponseEntity.ok(new ApiResponse(true,"Post",draft));
    }

    @GetMapping(path = "/user")
    public PagedResponse<?> getDraftByUser(@CurrentUser CustomUserDetails currentUser,
                                           @RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                           @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
                                           @RequestParam(name = "sort" ,defaultValue = "DESC") String sort){
        Long userId = currentUser.getUserId();
        return draftService.getDraftsByUser(page,size,sort,userId);
    }

    @GetMapping("")
    public PagedResponse<?> getAllDrafts( @RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                          @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
                                          @RequestParam(name = "sort" ,defaultValue = "DESC") String sort){
        return draftService.getAllDrafts(page,size,sort);
    }
}
