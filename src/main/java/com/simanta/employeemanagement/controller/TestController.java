package com.simanta.employeemanagement.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/hello")
    public String hello(Authentication authentication) {

//        return "Hello " + authentication.getName() + ", you are authenticated!";
        return String.format("Hello %s, you are authenticated!", authentication.getName());
    }

    @GetMapping("/admin")
    public String admin(Authentication authentication) {
        return "Hello Admin " + authentication.getName() + ", you have ADMIN access!";
    }
}
