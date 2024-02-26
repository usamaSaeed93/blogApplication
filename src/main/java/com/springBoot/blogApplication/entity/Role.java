package com.springBoot.blogApplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    @NotBlank(message = "Role name is mandatory")
    private String name;

    // Add a constructor that takes the role name as a parameter
    public Role(String name) {
        this.name = name;
    }
}

