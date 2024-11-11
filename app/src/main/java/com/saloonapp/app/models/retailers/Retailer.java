package com.saloonapp.app.models.retailers;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table
public class Retailer {

    @Id
    private String retailerId;
    private String retailerName;
    private String reatilerAddress;
    private String retailerOwner;
    private String retailerUsername;
    private String	retailerPass;	
    private String  retailerMobile;	
    private String  retailerEmail;
    private String  retailerDescription;
    private String  rating;
    private String  retailerLocationId;
    private String  timings;	
    private String  noOfBarbers;
    private String  isAvailable;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[]  retailImage;

    @Transient
    private List<Services> serviceList; 
    

    

  
    
}
