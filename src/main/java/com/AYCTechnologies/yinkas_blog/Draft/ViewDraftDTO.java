package com.AYCTechnologies.yinkas_blog.Draft;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ViewDraftDTO {

    private Long draftId;

    private Long userId;

    private String content;

    private String lastModifiedDate;

    private String createdBy;
}
