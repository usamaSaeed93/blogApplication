package com.springBoot.blogApplication.controller;

import com.springBoot.blogApplication.entity.Posts;
import com.springBoot.blogApplication.exception.PostCreationException;
import com.springBoot.blogApplication.payload.ErrorDetails;
import com.springBoot.blogApplication.payload.PostDTO;
import com.springBoot.blogApplication.services.PostsService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostsController {
    private final PostsService postService;
//
    @Autowired
    public PostsController(PostsService postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public ResponseEntity<ErrorDetails> createPost(@Valid @RequestBody PostDTO postDTO, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                String errorMessage = "Validation error: " + bindingResult.getFieldError().getDefaultMessage();
                throw new PostCreationException(errorMessage);
            }
            postService.createPost(postDTO);
            return new ResponseEntity<>(new ErrorDetails(200, "Post created successfully",
                    String.valueOf(new Date().getTime())),
                    HttpStatus.OK);
        } catch (PostCreationException e) {
            ErrorDetails errorDetails = new ErrorDetails(400, "Error creating post: " + e.getMessage(),
                    String.valueOf(new Date().getTime()));
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails(500, "Internal Server Error: " + e.getMessage(),
                    String.valueOf(new Date().getTime()));
            return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<ErrorDetails> updatePost(@Valid @RequestBody PostDTO postDTO, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                String errorMessage = "Validation error: " + bindingResult.getFieldError().getDefaultMessage();
                throw new PostCreationException(errorMessage);
            }
            postService.updatePost(postDTO);
            return new ResponseEntity<>(new ErrorDetails(200, "Post updated successfully",
                    String.valueOf(new Date().getTime())),
                    HttpStatus.OK);
        } catch (PostCreationException e) {
            ErrorDetails errorDetails = new ErrorDetails(400, "Error updating post: " + e.getMessage(),
                    String.valueOf(new Date().getTime()));
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails(500, "Internal Server Error: " + e.getMessage(),
                    String.valueOf(new Date().getTime()));
            return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ErrorDetails> deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            return new ResponseEntity<>(new ErrorDetails(200, "Post deleted successfully",
                    String.valueOf(new Date().getTime())),
                    HttpStatus.OK);
        } catch (RuntimeException e) {
            ErrorDetails errorDetails = new ErrorDetails(500, "Internal Server Error: " + e.getMessage(),
                    String.valueOf(new Date().getTime()));
            return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPosts() {
        try {
            List<Posts> posts = postService.getAllPosts();
            return ResponseEntity.status(HttpStatus.OK).body(posts);
        } catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails(500, "Internal Server Error: " + e.getMessage(),
                    String.valueOf(new Date().getTime()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
        }
    }

}