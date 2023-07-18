package com.AYCTechnologies.yinkas_blog.Service;

import com.AYCTechnologies.yinkas_blog.Config.AppConstants;
import com.AYCTechnologies.yinkas_blog.Exceptions.BadRequestException;
import com.AYCTechnologies.yinkas_blog.Response.PagedResponse;
import com.AYCTechnologies.yinkas_blog.User.Requests.ResetPasswordRequest;
import com.AYCTechnologies.yinkas_blog.User.User;
import com.AYCTechnologies.yinkas_blog.User.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper modelMapper;


    public void activateAccount(String email){
        User user = userRepository.findByEmail(email);
        user.setUserId(user.getUserId());
        user.setIsActivated(Boolean.TRUE);
        User rs = userRepository.save(user);
    }

    public void disableAccount(String email){
        User user = userRepository.findByEmail(email);
        user.setUserId(user.getUserId());
        user.setIsDisabled(Boolean.TRUE);
        user.setIsActivated(Boolean.FALSE);
        User rs = userRepository.save(user);
    }

    public Boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public void resetPassword(String email, ResetPasswordRequest resetPasswordRequest) {
        if(!existsByEmail(email)) throw new BadRequestException("Account doesn't exist on the system");
        User user = userRepository.findByEmail(email);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(

                        email,
                        resetPasswordRequest.getPassword()
                )
        );

       String newPasswordEncrypted = passwordEncoder.encode(resetPasswordRequest.getNewPassword());

       user.setUserId(user.getUserId());
       user.setPassword(newPasswordEncrypted);
       User rs  = userRepository.save(user);

    }

    public void updateLastLogin(String email, String dateTime){
        User user = userRepository.findByEmail(email);
        user.setUserId(user.getUserId());
        user.setLastLogin(dateTime);
        User rs = userRepository.save(user);
    }


    public User findUserById(Long id){
        User user = userRepository.findByUserId(id);
        if (Objects.isNull(user)) throw new BadRequestException("User doesn't exist ");
        return  user;
    }


    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }
}

