package com.springBoot.blogApplication.services.impl;
import com.springBoot.blogApplication.entity.Posts;
import com.springBoot.blogApplication.payload.PostDTO;
import com.springBoot.blogApplication.repository.PostRepository;
import com.springBoot.blogApplication.repository.UserRepository;
import com.springBoot.blogApplication.services.PostsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PostServiceImplementation implements PostsService {
    private final UserRepository userRepository;
    private final PostRepository   postRepository;

    public PostServiceImplementation(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public String createPost(PostDTO postDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            System.out.println("Username yiwhbkseadx: " + username);
            Posts post = new Posts();
            post.setTitle(postDTO.getTitle());
            post.setContent(postDTO.getContent());
            post.setCategory(postDTO.getCategory());
            post.setCreationDate(String.valueOf(new Date()));
            post.setUser(userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found iwaj")));
            postRepository.save(post);
            return "Created post successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to create post";
        }

    }
}
