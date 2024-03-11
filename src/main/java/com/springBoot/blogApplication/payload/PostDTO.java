package com.springBoot.blogApplication.payload;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Content cannot be empty")
    private String content;

    @NotBlank(message = "Category cannot be empty")
    private String category;

    private Long id;
}
