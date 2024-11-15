package com.saloonapp.app.config.identity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.saloonapp.app.models.identity.UserCredential;
import com.saloonapp.app.repos.identity.UserCredentialRepository;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialRepository repository;

    
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredential> credential = repository.findByName(username);
        return credential.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("user not found with name :" + username));
    }

    public String addUser(UserCredential userInfo) {
        // Encode password before saving the user
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User Added Successfully";
    }
    public String updateUser(UserCredential userInfo) {
        UserCredential existingUser = repository.findByName(userInfo.getName()).orElseThrow(() -> new UsernameNotFoundException("user not found with name :" + userInfo.getName()));

        existingUser.setEmail(userInfo.getEmail());
        // Encode password before saving the user
        existingUser.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(existingUser);
        return "User Updated Successfully";
    }
    public String updateUserPassword(String username, String newPassword) {
        UserCredential userInfo = repository.findByName(username).orElseThrow(() -> new UsernameNotFoundException("user not found with name :" + username));
        // Encode password before saving the user
        if (userInfo.getId() != null) {
            
    
        userInfo.setPassword(encoder.encode(newPassword));
        repository.save(userInfo);
        return "User Updated Successfully";
        }
        else throw new UsernameNotFoundException("user not found with name :" + username);
    }
}
/*
 *  @Autowired
    private UserInfoRepository repository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = repository.findByEmail(username); // Assuming 'email' is used as username

        // Converting UserInfo to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

  
 */
