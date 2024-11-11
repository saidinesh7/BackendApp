package com.saloonapp.app.dto.retailers;






import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import com.saloonapp.app.models.retailers.Services;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetailerDto {
    
    private String retailerId;
    private String  retailerName;//
    private String  reatilerAddress;//
    private String  retailerOwner;//
    private String  retailerMobile;	//
    private String  retailerEmail;//
    private String  retailerDescription;//
    private String  rating;
    private String  retailerLocationId;
    private String  timings;	//
    private String  noOfBarbers;//
    private String  isAvailable;//
    private byte[]  retailImage;//
    private List<Services> serviceList;
    


}
