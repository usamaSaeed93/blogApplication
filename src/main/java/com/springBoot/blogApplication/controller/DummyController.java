package com.springBoot.blogApplication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {
    @GetMapping("helloWorld")
    public String helloWorld(){
        return "Hello World";
    }
}
