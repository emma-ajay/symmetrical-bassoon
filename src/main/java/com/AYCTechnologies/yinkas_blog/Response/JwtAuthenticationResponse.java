package com.AYCTechnologies.yinkas_blog.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Object profile;

    private Object role;

    public JwtAuthenticationResponse(String accessToken,Object profile,Object role) {
        this.accessToken = accessToken;
        this.profile = profile;
        this.role = role;
    }


}