package com.app.AppApplication.service;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.app.AppApplication.model.User;
import com.app.AppApplication.repository.UserRepository;
import com.app.AppApplication.service.EmailService;
import com.app.AppApplication.service.S3Service;

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
        if (userRepository.existsByUserMail(user.getEmail())){
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
}
