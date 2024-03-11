package com.springBoot.blogApplication.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @NotBlank(message = "Category cannot be empty")
    @Column(name = "category")
    private String category;
    @Nullable
    @Column(name = "last_updated")
    private String lastUpdated;
@Nullable
    @Column(name = "creation_date")
    private String creationDate;

@ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private Integer likes;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comment> comments;


}
