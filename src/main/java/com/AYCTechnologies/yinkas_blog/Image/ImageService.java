package com.AYCTechnologies.yinkas_blog.Image;

import com.azure.storage.blob.BlobClientBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageService {
    @Autowired
    BlobClientBuilder client;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ImageRepository imageRepository;

    public Image uploadNewImage(MultipartFile file){

        Image image = new Image();
        String imageUrl = upload(file);
        image.setImageUrl(imageUrl);

       return imageRepository.save(image);

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
