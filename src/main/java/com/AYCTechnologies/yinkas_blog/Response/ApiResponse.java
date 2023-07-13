package com.AYCTechnologies.yinkas_blog.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private Boolean success;
    private String message;

    private Object object;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

}