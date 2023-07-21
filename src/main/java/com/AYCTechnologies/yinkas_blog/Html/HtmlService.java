package com.AYCTechnologies.yinkas_blog.Html;

import com.azure.storage.blob.BlobClientBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
public class HtmlService {
    @Autowired
    BlobClientBuilder client;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    HtmlRepository htmlRepository;


    public MultipartFile createHtml(String content) {
        String path = "src/main/java/com/AYCTechnologies/yinkas_blog/Html/Post.html";

        try {
            File filepath = new File(path);

            FileWriter fileWriter = new FileWriter(filepath);
            fileWriter.write(content);
            fileWriter.close();

            MultipartFile multipartFile = new MockMultipartFile("Post.html", new FileInputStream(new File(path)));
            return multipartFile;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Html uploadNewHtml(String content, String userName, String createdDate, String type,  Long postId){

        MultipartFile file = createHtml(content);

        Html html = new Html();
        String htmlUrl = upload(file);
        html.setFileUrl(htmlUrl);
        html.setCreatedBy(userName);
        if (Objects.equals(type,"draft")){
            html.setDraftId(postId);
            html.setPostId(null);
        }
        html.setPostId(postId);
        html.setCreatedDate(createdDate);
        return htmlRepository.save(html);

    }
    public String upload(MultipartFile file){
        if(Objects.nonNull(file)&& file.getSize()>0){
            String defaultUrl = "https://yinkablog.blob.core.windows.net/yinkasblog/";
            String imagePath;
            try {
                // name file
                String fileName =  UUID.randomUUID().toString() + "html.txt";

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
