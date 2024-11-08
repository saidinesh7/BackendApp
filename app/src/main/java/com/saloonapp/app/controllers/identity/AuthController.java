package com.saloonapp.app.controllers.identity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.saloonapp.app.config.identity.CustomUserDetailsService;
import com.saloonapp.app.config.identity.JwtService;
import com.saloonapp.app.dto.identity.AuthRequest;
import com.saloonapp.app.models.identity.UserCredential;



@CrossOrigin(origins = "*",allowCredentials = "true",allowedHeaders = "*")
@RestController
//@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private CustomUserDetailsService service;

    @Autowired
    private JwtService jwtService;

   

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String homePage(){
        return "Hello World";
    }

    @PostMapping("/auth/register")
    public String addNewUser(@RequestBody UserCredential user) {
        return service.addUser(user);
    }

    @PostMapping("/auth/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    // @GetMapping("/validate")
    // public String validateToken(@RequestParam("token") String token) {
    //     service.validateToken(token);
    //     return "Token is valid";
    // }
}

/**
 * 
 * @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return service.addUser(userInfo);
    }

    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
 */
