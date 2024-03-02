package com.springBoot.blogApplication.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Data
@AllArgsConstructor
@Getter
@Setter
public class ErrorDetails {
    private String message;
    private String details;
    private Date timeStamp;
}
