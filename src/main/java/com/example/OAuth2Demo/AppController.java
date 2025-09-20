package com.example.OAuth2Demo;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class AppController {

    private Logger LOGGER = Logger.getLogger(AppController.class.getName());

    @GetMapping("/hello")
    public String gethello() {
        return "Hello World"+Thread.currentThread().getId();
    }


    @GetMapping("/user-details")
    public Map<String,Object> userDetails(){
        LOGGER.info("Processing USER DETAILS API");
        OAuth2User user = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getAttributes();
    }
}
