package com.springBoot.blogApplication.filter;

import com.springBoot.blogApplication.entity.User;
import com.springBoot.blogApplication.services.JWTService;
import com.springBoot.blogApplication.services.impl.UserDetailsServiceImp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserDetailsServiceImp userDetailsService;

    public JwtAuthenticationFilter(JWTService jwtService, UserDetailsServiceImp userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("JwtAuthenticationFilter: Intercepting request...");

        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("JwtAuthenticationFilter: No Bearer token found. Skipping authentication.");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUserName(token);

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("JwtAuthenticationFilter: Validating token...");

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtService.isValid(token, userDetails)) {
                System.out.println("JwtAuthenticationFilter: Token is valid. Creating authentication object.");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                System.out.println("JwtAuthenticationFilter: Token is not valid.");
                System.out.println("JwtAuthenticationFilter: Token expiration: " + jwtService.isTokenExpired(token));
                System.out.println("JwtAuthenticationFilter: Current date: " + new Date());
                // Add more details for debugging
                System.out.println("JwtAuthenticationFilter: Username from token: " + username);
                System.out.println("JwtAuthenticationFilter: UserDetails username: " + userDetails.getUsername());
            }
        }

        filterChain.doFilter(request, response);
        System.out.println("JwtAuthenticationFilter: Request processed. Authentication completed if applicable.");
    }
}
