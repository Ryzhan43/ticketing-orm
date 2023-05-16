package com.mryzhan.config;

import com.mryzhan.dto.UserDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder){

        List<UserDetails> userList = new ArrayList<>();

        userList.add(
          new User("mike", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))));
          new User("danny", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER")));

          return new InMemoryUserDetailsManager(userList);
    }


    public SecurityFilterChain filterChain(HttpSecurity http){
        return http
                .authorizeRequests()
                .antMatchers(
                        "/",
                     "/login",
                        "/fragments",
                        "/assets/**",
                        "images/**"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and().build();
    }

}
