package com.saloonapp.app.models.rah;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;



@Entity
@Data
public class CustomerServices {

   @Id
   private String serviceId;	
   private String serviceName;	
   private String serviceCost;
   private String retailerId;
   private String customerId;
    private String images;
    private String duration;
    private String serviceType;

}

