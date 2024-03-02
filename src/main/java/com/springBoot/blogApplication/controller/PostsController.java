package com.springBoot.blogApplication.controller;

import com.springBoot.blogApplication.payload.PostDTO;
import com.springBoot.blogApplication.services.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<String> createPost(@RequestBody PostDTO postDTO) {
       postService.createPost(postDTO);
        return ResponseEntity.ok("Post created successfully");
    }
}
