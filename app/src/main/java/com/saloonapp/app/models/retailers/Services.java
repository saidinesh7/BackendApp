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
public class Services {

   @Id
   private String serviceId;	
   private String serviceName;	
   private String serviceCost;
   private String retailerId;
    private String images;
    private String duration;
    private String serviceType;

}

