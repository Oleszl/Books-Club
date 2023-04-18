package com.excellentbook.excellentbook.config;

import com.excellentbook.excellentbook.security.jwt.JwtAuthenticationEntryPoint;
import com.excellentbook.excellentbook.security.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().disable().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.DELETE,
                        "/api/v1/users")
                .hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET,
                        "/api/v1/users/{userId}/personal-books",
                        "/api/v1/users/{userId}/books",
                        "/api/v1/users")
                .authenticated()
                .requestMatchers(HttpMethod.POST,
                        "/api/v1/books",
                        "/api/v1/books/{bookId}/users/{userId}",
                        "/api/v1/books/{bookId}/users/{userId}/approve")
                .authenticated()
                .requestMatchers(HttpMethod.PATCH,
                        "/api/v1/books/{bookId}",
                        "/api/v1/users/{userId}")
                .authenticated()
                .requestMatchers(HttpMethod.PUT,
                        "/api/v1/users/{userId}",
                        "/api/v1/users/{userId}/address")
                .authenticated()
                .anyRequest().permitAll();

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
