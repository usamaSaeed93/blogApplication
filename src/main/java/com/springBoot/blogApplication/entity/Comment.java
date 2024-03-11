package com.springBoot.blogApplication.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "comment")
    private String comment;
    @Column(name = "date_created")
    private String dateCreated;
    @Column(name = "date_updated")
    private String dateUpdated;
    @Column(name = "likes")
    private Integer likes;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posts post;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", dateUpdated='" + dateUpdated + '\'' +
                ", likes=" + likes +
                ", post=null" +  // Exclude the post reference
                '}';
    }

}
