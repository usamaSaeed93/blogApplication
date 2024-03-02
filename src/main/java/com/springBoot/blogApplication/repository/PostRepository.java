package com.springBoot.blogApplication.repository;

import com.springBoot.blogApplication.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.*;
import java.util.List;

public interface PostRepository extends JpaRepository<Posts, Long>{
    @Query("SELECT p FROM Posts p WHERE p.title LIKE %?1%")
    public List<Posts> findByTitle(String title);
//    public List<Posts> findByCategory(String category);
//    public List<Posts> findByCreationDate(String creationDate);
//    public List<Posts> findByUser(String user);
}
