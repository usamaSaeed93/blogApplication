package com.springBoot.blogApplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "posts")
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "title")
    @NotBlank(message = "Title cannot be empty")
    private String title;
    @NotBlank(message = "Content cannot be empty")
    @Column(name = "content")
    private String content;
    @NotBlank(message = "Author cannot be empty")
    private String creationDate;
    @NotBlank(message = "Category cannot be empty")
    @Column(name = "category")
    private String category;
    @Column(name = "last_updated")
    private String lastUpdated;

@ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
