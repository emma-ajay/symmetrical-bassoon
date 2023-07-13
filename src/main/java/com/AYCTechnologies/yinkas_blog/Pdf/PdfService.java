package com.AYCTechnologies.yinkas_blog.Pdf;

import com.azure.storage.blob.BlobClientBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
public class PdfService {
    @Autowired
    BlobClientBuilder client;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PdfRepository pdfRepository;

    public Pdf uploadNewPdf(MultipartFile file){

        Pdf pdf = new Pdf();
        String pdfUrl = upload(file);
        pdf.setFileUrl(pdfUrl);

        return pdfRepository.save(pdf);

    }
    public String upload(MultipartFile file){
        if(Objects.nonNull(file)&& file.getSize()>0){
            String defaultUrl = "https://yinkablog.blob.core.windows.net/yinkasblog/";
            String imagePath;
            try {
                // name file
                String fileName =  UUID.randomUUID().toString() +file.getOriginalFilename();

                client.blobName(fileName).buildClient().upload(file.getInputStream(),file.getSize());
                imagePath = defaultUrl+fileName;
                return imagePath;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
