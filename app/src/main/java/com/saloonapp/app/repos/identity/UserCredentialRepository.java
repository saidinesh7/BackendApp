package com.saloonapp.app.repos.identity;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saloonapp.app.models.identity.UserCredential;

import java.util.Optional;

public interface UserCredentialRepository  extends JpaRepository<UserCredential,String> {
    Optional<UserCredential> findByName(String username);
}
