package com.AYCTechnologies.yinkas_blog.User.Response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    private Long userId;

    private String email;

    private String userName;

    private String name;

    private String phoneNumber;


}
