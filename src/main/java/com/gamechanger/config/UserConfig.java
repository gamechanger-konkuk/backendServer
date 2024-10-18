package com.gamechanger.config;

import com.gamechanger.repository.UserRepository;
import com.gamechanger.service.ClothesService;
import com.gamechanger.service.UserDetailsServiceImpl;
import com.gamechanger.service.UserService;
import com.gamechanger.service.UserServiceImpl;
import com.gamechanger.util.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class UserConfig {

    private final UserRepository userRepository;
    private final ClothesService clothesService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserConfig(UserRepository userRepository, ClothesService clothesService, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.clothesService = clothesService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userRepository, clothesService, jwtTokenProvider);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(userRepository);
    }
}
