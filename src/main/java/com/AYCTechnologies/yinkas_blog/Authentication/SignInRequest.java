package com.AYCTechnologies.yinkas_blog.Authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SignInRequest {
    private String email;

    private String password;

    private  String lastLogin;

}

