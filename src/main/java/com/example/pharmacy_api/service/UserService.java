package com.example.pharmacy_api.service;

import com.example.pharmacy_api.model.entity.User;
import com.example.pharmacy_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username).orElseThrow(
                        () -> new UsernameNotFoundException("User not found")
                );
            }
        };
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }
}
