package com.AYCTechnologies.yinkas_blog.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper(){

        return new ModelMapper();
    }
}
