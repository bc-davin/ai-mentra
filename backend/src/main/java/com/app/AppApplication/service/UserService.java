package com.app.AppApplication.service;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Duration;
import java.net.URL;
import java.security.SecureRandom;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.app.AppApplication.model.User;
import com.app.AppApplication.repository.UserRepository;



@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Service s3Service;
    private final EmailService emailService;
    
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, S3Service s3Service, EmailService emailService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.s3Service = s3Service;
    }

    public User createUser(User user){
        if (userRepository.existsByUserMail(user.getUserEmail())){
            throw new RuntimeException("User already exists");
        }
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        user.setEnabled(true);
        user.setMDatetime(LocalDateTime.now());
        user.setRDatetime(LocalDateTime.now());

        return userRepository.save(user);
    }

    public User updateUser(Long id, User updated){
        User user = getUserById(id);
        user.setUserName(updated.getUserName());
        user.setUserSchool(updated.getUserSchool());
        if (updated.getUserPassword()!=null){
            user.setUserPassword(passwordEncoder.encode(updated.getUserPassword()));
        }

        return userRepository.save(user);
    }

     public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
    public void resetPassword(User user){
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        String tempPassword = sb.toString();
        user.setUserPassword(passwordEncoder.encode(tempPassword));
        userRepository.save(user);

        emailService.sendPassWordResetEmail(user.getUserName(), user.getUserEmail(), tempPassword);
    }
    public URL getProfilePictureUploadUrl(User user, String fileName,String contentType){
        String s3Key = "uploads/profile-pictures/" + fileName;
        user.setProfilePictureUrl(s3Key);
        userRepository.save(user);
        //return s3Service.gen
        return s3Service.generateUploadUrl(s3Key, contentType, Duration.ofHours(3));
    }

}
