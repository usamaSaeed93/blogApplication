package com.springBoot.blogApplication.filter;

import com.springBoot.blogApplication.services.JWTService;
import com.springBoot.blogApplication.services.impl.UserDetailsServiceImp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserDetailsServiceImp userDetail;

    public JWTAuthenticationFilter(JWTService jwtService, UserDetailsServiceImp userDetail) {
        this.jwtService = jwtService;
        this.userDetail = userDetail;
    }


    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws
            ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorizationHeader.substring(7);
        String userName = jwtService.extractUserName(token);
        UserDetails user = userDetail.loadUserByUsername(userName);


        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userName, null, user.getAuthorities());

            usernamePasswordAuthenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );


            filterChain.doFilter(request, response);
        }

    }
}
