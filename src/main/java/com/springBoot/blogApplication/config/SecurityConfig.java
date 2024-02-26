package com.springBoot.blogApplication.config;


import com.springBoot.blogApplication.filter.JWTAuthenticationFilter;
import com.springBoot.blogApplication.services.impl.UserDetailsServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImp userDetailsServiceImp;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(UserDetailsServiceImp userDetailsServiceImp, JWTAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(
                        req -> req.requestMatchers("/api/auth/**").permitAll().requestMatchers("/").permitAll()
//                                .anyRequest().authenticated()
                ).userDetailsService(userDetailsServiceImp).sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();
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
