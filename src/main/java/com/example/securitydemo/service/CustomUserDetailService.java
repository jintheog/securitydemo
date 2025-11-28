package com.example.securitydemo.service;

import com.example.securitydemo.repository.UserRepository;
import com.example.securitydemo.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    pubilc UserDetails loadUserByUsername(String username) {
        USer user = userRepository.findByUsername(username)
                .orElseThrow(() -> UsernameNotFoundException(username));

        return new CustomUserDetails(user);
    }

}
