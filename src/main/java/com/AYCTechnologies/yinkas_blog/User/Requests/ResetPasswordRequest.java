package com.AYCTechnologies.yinkas_blog.User.Requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class ResetPasswordRequest {
    private String password;

    private String newPassword;
}
