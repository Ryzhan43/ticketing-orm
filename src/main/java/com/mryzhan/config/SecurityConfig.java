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

        List<UserDetails> userList = Arrays.asList(new User("mike", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))),
                                                    new User("danny", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER")))
        );

          return new InMemoryUserDetailsManager(userList);
    }


    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                .antMatchers("user/**").hasRole("ADMIN")
                .antMatchers("project/**").hasRole("MANAGER")
                .antMatchers("task/employee/**").hasRole("EMPLOYEE")
//                .antMatchers("task/**").hasAnyRole("EMPLOYEE", "ADMIN")
//                .antMatchers("task/**").hasAuthority("ROLE_EMPLOYEE")
                .antMatchers(
                        "/",
                     "/login",
                        "/fragments",
                        "/assets/**",
                        "images/**"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
//                .httpBasic()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/welcome")
                .failureUrl("/login/error=true")
                .permitAll()
                .and().build();
    }

}
