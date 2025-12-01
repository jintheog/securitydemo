package com.example.securitydemo.config;

import com.example.securitydemo.entity.User;
import com.example.securitydemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        User user = User.builder()
                .username("testuser")
                .password(passwordEncoder.encode("1234"))
                .role("ROLE_USER")
                .email("test@test.com")
                .build();

        User user2 = User.builder()
                .username("testuser2")
                .password(passwordEncoder.encode("1234"))
                .role("ROLE_USER")
                .email("test2@test2.com")
                .build();


        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .role("ROLE_ADMIN")
                .email("admin@test.com")
                .build();

        userRepository.save(user);
        userRepository.save(admin);

        System.out.println("user 데이터 생성완료");
    }
}
