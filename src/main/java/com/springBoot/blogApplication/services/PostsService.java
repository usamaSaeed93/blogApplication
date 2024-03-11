package com.springBoot.blogApplication.services;

import com.springBoot.blogApplication.entity.Posts;
import com.springBoot.blogApplication.payload.PostDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostsService  {
public ResponseEntity<String> createPost(PostDTO postDTO);
public ResponseEntity<String> updatePost(PostDTO postDTO);

    public ResponseEntity<String> deletePost(Long id);

   public List<Posts> getAllPosts();
}
