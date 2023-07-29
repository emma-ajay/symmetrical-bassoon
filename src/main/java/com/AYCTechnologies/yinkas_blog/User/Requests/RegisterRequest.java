package com.AYCTechnologies.yinkas_blog.User.Requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String email;

    private String name;

    private String phoneNumber;

    private String userName;

    private String createdDate;

    private String password;
  



}
