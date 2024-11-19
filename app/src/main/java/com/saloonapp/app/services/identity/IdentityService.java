package com.saloonapp.app.services.identity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.saloonapp.app.models.identity.UserCredential;
import com.saloonapp.app.repos.identity.UserCredentialRepository;

@Service
public class IdentityService {

    @Autowired
    UserCredentialRepository uRepository;

    public UserCredential getUserRole(String username) {
        return uRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException(" invalid username "));
    }

}
