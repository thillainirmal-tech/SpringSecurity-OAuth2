package com.example.OAuth2Demo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class LastAccessFilter extends HttpFilter {

    private static final Logger log = LoggerFactory.getLogger(LastAccessFilter.class);

    @Autowired
    private UserRepo userRepo;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {
            OAuth2User auth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (auth2User != null) {
                String email = auth2User.getAttribute("email");
                User myUser = userRepo.findByEmail(email);
                if (myUser == null) {
                    String name = auth2User.getAttribute("name");
                    myUser = new User();
                    myUser.setName(name);
                    myUser.setEmail(email);
                }
                myUser.setLastAccess(new Date());
                userRepo.save(myUser);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        chain.doFilter(request, response);


    }
}



