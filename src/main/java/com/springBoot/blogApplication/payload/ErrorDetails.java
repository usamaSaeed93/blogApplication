package com.springBoot.blogApplication.payload;

import lombok.*;

import java.util.Date;
@Data
@Getter
@Setter
public class ErrorDetails {
    private String message;
    private Integer status;
    private String timeStamp;

    public ErrorDetails(Integer status, String message, String timeStamp) {
        this.message = message;
        this.status = status;
        this.timeStamp = timeStamp;
    }
}
