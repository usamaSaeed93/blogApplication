package com.springBoot.blogApplication.payload;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private String title;
    private String content;
    private String username;
    private String category;

}
