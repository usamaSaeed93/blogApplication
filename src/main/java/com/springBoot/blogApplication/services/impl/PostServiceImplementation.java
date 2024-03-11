package com.springBoot.blogApplication.services.impl;

import com.springBoot.blogApplication.entity.Posts;
import com.springBoot.blogApplication.exception.PostCreationException;
import com.springBoot.blogApplication.payload.PostDTO;
import com.springBoot.blogApplication.repository.PostRepository;
import com.springBoot.blogApplication.repository.UserRepository;
import com.springBoot.blogApplication.services.PostsService;
import jakarta.security.auth.message.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImplementation implements PostsService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public PostServiceImplementation(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public ResponseEntity<String> createPost(PostDTO postDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Posts post = new Posts();
            post.setTitle(postDTO.getTitle());
            post.setContent(postDTO.getContent());
            post.setCategory(postDTO.getCategory());
            post.setCreationDate(String.valueOf(new Date()));
            post.setUser(userRepository.findByUsername(username)
                    .orElseThrow(() -> new PostCreationException("User not found")));
            postRepository.save(post);
            return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully");
        } catch (Exception e) {
            throw new PostCreationException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> updatePost(PostDTO postDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Posts post = postRepository.findById(postDTO.getId())
                    .orElseThrow(() -> new PostCreationException("Post not found"));

            if (post.getUser().getUsername().equals(username)) {
                post.setTitle(postDTO.getTitle());
                post.setContent(postDTO.getContent());
                post.setCategory(postDTO.getCategory());
                post.setLastUpdated(String.valueOf(new Date()));
                postRepository.save(post);
                return ResponseEntity.status(HttpStatus.OK).body("Post updated successfully");
            } else {
                throw new PostCreationException("You are not authorized to update this post");
            }
        } catch (Exception e) {
            throw new PostCreationException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deletePost(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("Username: " + username);

        try {
            Posts post = postRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Post not found"));
            System.out.println("Found Post: " + post);

            if (post.getUser().getUsername().equals(username)) {
                postRepository.delete(post);
                return ResponseEntity.status(HttpStatus.OK).body("Post deleted successfully");
            } else {
                throw new AuthException("You are not authorized to delete this post");
            }
        } catch (StackOverflowError e) {
            System.out.println("StackOverflowError while deleting post: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting post: " + e.getMessage());
        } catch (AuthException e) {
            // Handle authorization exception
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (RuntimeException e) {
            // Catch runtime exceptions, log, and rethrow
            System.out.println("Error deleting post: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            // Catch general exceptions and handle accordingly
            System.out.println("Error deleting post: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting post: " + e.getMessage());
        }
    }

    public List<Posts> getAllPosts() {
        try {
            List<Posts> posts = postRepository.findAll();

            if (!posts.isEmpty()) {
                return posts;
            } else {
                // If no posts are found, you can return an empty list or null based on your preference
                return List.of();
            }
        } catch (Exception e) {
            // Log the exception and throw a more specific exception or return an error response
            System.out.println("Error retrieving posts: " + e.getMessage());
            throw new RuntimeException("Error retrieving posts: " + e.getMessage());
        }
    }
}
