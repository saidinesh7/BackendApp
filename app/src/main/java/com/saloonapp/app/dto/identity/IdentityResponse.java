package com.saloonapp.app.dto.identity;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class IdentityResponse {

    private String token;
    private String role;
    private Date expiresIn;

    public IdentityResponse(String token, String role, java.util.Date expiresIn) {
        this.token = token;
        this.role = role;
        this.expiresIn = expiresIn;
    }

    

}
