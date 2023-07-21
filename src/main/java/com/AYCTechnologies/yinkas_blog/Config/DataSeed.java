package com.AYCTechnologies.yinkas_blog.Config;

import com.AYCTechnologies.yinkas_blog.Role.Role;
import com.AYCTechnologies.yinkas_blog.Role.RoleName;
import com.AYCTechnologies.yinkas_blog.Role.RoleRepository;
import com.AYCTechnologies.yinkas_blog.User.User;
import com.AYCTechnologies.yinkas_blog.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Collections;
import java.util.Objects;

@Configuration
public class DataSeed {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;


    @Bean
    CommandLineRunner commandLineRunner (UserRepository userRepository){
        String password = passwordEncoder.encode("password");
        Long time = Instant.now().getEpochSecond();
        String currentDate = String.valueOf(time*1000L);

        return args -> {

            Role role = roleRepository.findRole(RoleName.ROLE_ADMIN);
            User user = userRepository.findByEmail("emmanuelajayi205@gmail.com");


            if (role == null &&  user == null){
                Role role1 = new Role(RoleName.ROLE_ADMIN);
                roleRepository.save(role1);


                User user1 = new User(
                        "Ajayi Emmanuel",
                        "ajayy",
                        "emmanuelajayi205@gmail.com",
                        password,
                        Boolean.TRUE,
                        Boolean.FALSE,
                        "Ajayi Emmanuel",
                        currentDate

                );
                user1.setRoles(Collections.singleton(role1));
                userRepository.save(user1);
            }

            if(Objects.nonNull(roleRepository.findByName(RoleName.ROLE_CREATOR))){
                Role role1 = new Role(RoleName.ROLE_CREATOR);
                roleRepository.save(role1);
            }

            if(Objects.nonNull(roleRepository.findByName(RoleName.ROLE_USER))){
                Role role2 = new Role(RoleName.ROLE_USER);
                roleRepository.save(role2);
            }
        };
    }
}
