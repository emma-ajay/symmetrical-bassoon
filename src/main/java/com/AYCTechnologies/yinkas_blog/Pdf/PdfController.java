package com.AYCTechnologies.yinkas_blog.Pdf;

import com.AYCTechnologies.yinkas_blog.Exceptions.BadRequestException;
import com.AYCTechnologies.yinkas_blog.Response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping("api/v1/pdf")
public class PdfController {

    @Autowired
    PdfService pdfService;

    @PostMapping(path = "" ,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadPdf(@RequestPart("pdf") MultipartFile file ){
        if(Objects.isNull(file)) throw new BadRequestException("Include a pdf");
//      String userName = currentUser.getName();
        Pdf pdf = pdfService.uploadNewPdf(file);
        return ResponseEntity.ok(new ApiResponse(true,"Pdf Upload",pdf));
    }
}
