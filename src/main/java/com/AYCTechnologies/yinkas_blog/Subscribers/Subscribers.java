package com.AYCTechnologies.yinkas_blog.Subscribers;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subscribers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subscribers {
    @Id
    @SequenceGenerator(name = "subscriber_sequence",
            sequenceName = "subscriber_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "subscriber_sequence"
    )

    private Long subscriberId;

    private String email;

    private Boolean isDeleted;

}
