package com.AYCTechnologies.yinkas_blog.Image;


import com.AYCTechnologies.yinkas_blog.Exceptions.BadRequestException;
import com.AYCTechnologies.yinkas_blog.Response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping("api/v1/image")
public class ImageController {
    @Autowired
    ImageService imageService;

    @PostMapping(path = "" ,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadImage(@RequestPart("image")MultipartFile file){
        if(Objects.isNull(file)) throw new BadRequestException("Include an image");
//        String userName = currentUser.getName();
        Image image = imageService.uploadNewImage(file);
        return ResponseEntity.ok(new ApiResponse(true,"Image Upload",image));
    }

}
