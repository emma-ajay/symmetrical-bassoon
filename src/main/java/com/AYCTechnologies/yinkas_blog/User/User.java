package com.AYCTechnologies.yinkas_blog.User;

import com.AYCTechnologies.yinkas_blog.Role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
    @Table(name = "users", uniqueConstraints ={
            @UniqueConstraint( columnNames = {"email","userName"})
    })
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class User {
        @Id
        @SequenceGenerator(name = "user_sequence",
                sequenceName = "user_sequence",
                allocationSize = 1
        )
        @GeneratedValue(strategy = GenerationType.SEQUENCE,
                generator = "user_sequence"
        )

        private Long userId;

        private String name;

        private String userName;

        private String email;

        private String password;

        private Boolean isActivated;

        private Boolean isDisabled;

        private String createdDate;

        private String lastLogin;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    public User(String name, String userName, String email, String password, Boolean isActivated, Boolean isDisabled, String createdDate, String lastLogin) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.isActivated = isActivated;
        this.isDisabled = isDisabled;
        this.createdDate = createdDate;
        this.lastLogin = lastLogin;
    }
}
