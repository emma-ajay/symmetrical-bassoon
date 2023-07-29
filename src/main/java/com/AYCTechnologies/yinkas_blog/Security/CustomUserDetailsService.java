package com.AYCTechnologies.yinkas_blog.Security;



import com.AYCTechnologies.yinkas_blog.Exceptions.ResourceNotFoundException;
import com.AYCTechnologies.yinkas_blog.User.User;
import com.AYCTechnologies.yinkas_blog.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {
        //  Can be adjusted to let people login with either username or Email
        User user = userRepository.findByEmail(usernameOrEmail);
        if (Objects.isNull(user)){
            throw  new UsernameNotFoundException("User not found with Username or Email : " + usernameOrEmail);
        }

        return CustomUserDetails.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );

        return CustomUserDetails.create(user);
    }
}
