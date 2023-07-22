package com.AYCTechnologies.yinkas_blog.Authentication;


import com.AYCTechnologies.yinkas_blog.Exceptions.AppException;
import com.AYCTechnologies.yinkas_blog.Exceptions.BadRequestException;
import com.AYCTechnologies.yinkas_blog.Response.ApiResponse;
import com.AYCTechnologies.yinkas_blog.Response.JwtAuthenticationResponse;
import com.AYCTechnologies.yinkas_blog.Role.Role;
import com.AYCTechnologies.yinkas_blog.Role.RoleName;
import com.AYCTechnologies.yinkas_blog.Role.RoleRepository;
import com.AYCTechnologies.yinkas_blog.Security.CurrentUser;
import com.AYCTechnologies.yinkas_blog.Security.CustomUserDetails;
import com.AYCTechnologies.yinkas_blog.Security.JwtTokenProvider;
import com.AYCTechnologies.yinkas_blog.Service.EmailService;
import com.AYCTechnologies.yinkas_blog.Service.OtpService;
import com.AYCTechnologies.yinkas_blog.Service.UserService;
import com.AYCTechnologies.yinkas_blog.User.Requests.RegisterRequest;
import com.AYCTechnologies.yinkas_blog.User.Response.Profile;
import com.AYCTechnologies.yinkas_blog.User.User;
import com.AYCTechnologies.yinkas_blog.User.UserRepository;
import com.google.common.io.Files;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserService userService;
    @Autowired
    EmailService emailService;

    @Autowired
    OtpService otpService;

    @Autowired
    RoleRepository roleRepository;




    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody SignInRequest signInRequest, HttpServletRequest request) throws MessagingException, IOException {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getEmail(),
                        signInRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(signInRequest.getEmail());
        if(user.getIsDisabled()) throw new IllegalStateException("You've been disabled please contact system support");
        if(!user.getIsActivated()){
            otpService.clearOTP(signInRequest.getEmail());
            int otp = otpService.generateOTP(signInRequest.getEmail());
            authenticationMail(otp, user.getName(), user.getEmail(),request);
            throw new BadRequestException("Please check your email and validate your account ");
        }


        String jwt =tokenProvider.generateToken(authentication);
        userService.updateLastLogin(user.getEmail(),signInRequest.getLastLogin());
        Profile profile = modelMapper.map(user,Profile.class);
        Set<Role> role = user.getRoles();


        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt,profile,role));
    }


    @Transactional
    @PostMapping("/register/user")
    public ResponseEntity<? > registerUser(@RequestBody RegisterRequest registerRequest, HttpServletRequest request) throws MessagingException, IOException {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByUserName(registerRequest.getUserName())) {
            return new ResponseEntity(new ApiResponse(false, "Username already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = modelMapper.map(registerRequest, User.class);
        String setPassword = registerRequest.getName();
        String password = passwordEncoder.encode(setPassword);
        user.setPassword(password);

        user.setIsActivated(Boolean.FALSE);
        user.setIsDisabled(Boolean.FALSE);

        User result = userRepository.save(user);


        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));
        user.setRoles(Collections.singleton(userRole));

        int otp =otpService.generateOTP(user.getEmail());
        welcomeEmail(otp,user.getName(),user.getEmail(),request);

        Profile profile = modelMapper.map(result,Profile.class);
        return ResponseEntity.ok(new ApiResponse(true,"User registered successfully",profile));
    }

    @Transactional
    @PostMapping("/register/creator")
    public ResponseEntity<? > registerCreator(@RequestBody RegisterRequest registerRequest, HttpServletRequest request) throws MessagingException, IOException {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByUserName(registerRequest.getUserName())) {
            return new ResponseEntity(new ApiResponse(false, "Username already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = modelMapper.map(registerRequest, User.class);
        String setPassword = registerRequest.getName();
        String password = passwordEncoder.encode(setPassword);
        user.setPassword(password);

        user.setIsActivated(Boolean.FALSE);
        user.setIsDisabled(Boolean.FALSE);

        User result = userRepository.save(user);


        Role userRole = roleRepository.findByName(RoleName.ROLE_CREATOR)
                .orElseThrow(() -> new AppException("User Role not set."));
        user.setRoles(Collections.singleton(userRole));

        int otp =otpService.generateOTP(user.getEmail());
        welcomeEmail(otp,user.getName(),user.getEmail(),request);

        Profile profile = modelMapper.map(result,Profile.class);
        return ResponseEntity.ok(new ApiResponse(true,"User registered successfully",profile));
    }


    @RequestMapping(value="/activate/{email}",method = RequestMethod.GET)
    public String validateOtpLink (@PathVariable String email, @RequestParam(name = "otp") int otp, HttpServletRequest request) throws MessagingException, IOException {
        if (!userRepository.existsByEmail(email)){
            throw new BadRequestException("this user is not registered");
        }

        User user = userRepository.findByEmail(email);
        Boolean activatedCheck = user.getIsActivated();
        if(activatedCheck) throw  new BadRequestException("Your account is already activated");

        int otpValue = otpService.getOtp(email);
        if (otpValue<1) {
            int newOtp = otpService.generateOTP(email);
            authenticationMail(newOtp,email,email,request);
            throw new BadRequestException("No OTP generated or OTP has expired for your account check your email and validate new otp");
        }
        if(!Objects.equals(otpValue,otp)) throw new BadRequestException("OTP wrong");

        userService.activateAccount(email);
        otpService.clearOTP(email);


        return "VerificationSuccess";

    }

    @PostMapping("/activate/{email}")
    public ResponseEntity<?> validateOtp (@PathVariable String email, @RequestParam(name = "otp") int otp,HttpServletRequest request) throws MessagingException, IOException {
        if (!userRepository.existsByEmail(email)){
            throw new BadRequestException("this user is not registered");
        }
        User user = userRepository.findByEmail(email);
        Boolean activatedCheck = user.getIsActivated();
        if(activatedCheck) throw  new BadRequestException("Your account is already activated");
        int otpValue = otpService.getOtp(email);
        if (otpValue<1) {
            int newOtp = otpService.generateOTP(email);
            authenticationMail(newOtp,email,email,request);
            throw new BadRequestException("No OTP generated or OTP has expired for your account check your email and validate new otp");
        }
        if(!Objects.equals(otpValue,otp)) throw new BadRequestException("OTP wrong");

        userService.activateAccount(email);
        otpService.clearOTP(email);
        return ResponseEntity.ok(new ApiResponse(true, "account activated you can now sign in ", email));
    }

    public void authenticationMail(int otp, String name , String email,HttpServletRequest request) throws MessagingException, IOException {
        String activationUrl= activationUrl(email,request,otp);
        String content = Files.asCharSource(new File("src/main/java/com/ajayCodes/CDCAPI/emailTemp.html"), StandardCharsets.UTF_8).read();
        content = content.replace("[[URL]]",activationUrl);
        content = content.replace("[[username]]",name);
        content = content.replace("[[otp]]",String.valueOf(otp));

        emailService.sendMessage(email, "CDC OTP token to activate your account",content);


    }

    public void welcomeEmail(int otp, String name , String email,HttpServletRequest request) throws MessagingException, IOException {
        String activationUrl= activationUrl(email,request,otp);
        String content = Files.asCharSource(new File("src/main/resources/templates/otp.html"), StandardCharsets.UTF_8).read();
        content = content.replace("[[URL]]",activationUrl);
        content = content.replace("[[otp]]",String.valueOf(otp));


        emailService.sendMessage(email, "Welcome To The Lifestyle Blog "+ name,content);

    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    private String activationUrl(String email ,HttpServletRequest request, int otp){
        String siteUrl = getSiteURL(request);
        return siteUrl+"/activate/"+email+"?otp=" + String.valueOf(otp) ;
    }






}
