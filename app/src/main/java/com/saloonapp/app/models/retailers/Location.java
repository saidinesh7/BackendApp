package com.saloonapp.app.models.retailers;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Location {



    @Id
    private String locationId;
    private String locationName;
    private String state;
    private String country;
    private String pinCode;


}
