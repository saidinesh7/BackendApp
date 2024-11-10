package com.saloonapp.app.controllers.retailers;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saloonapp.app.dto.retailers.RetailerDto;
import com.saloonapp.app.models.retailers.Retailer;
import com.saloonapp.app.models.retailers.Services;

import com.saloonapp.app.services.retailers.RetServices;
import com.saloonapp.app.services.retailers.RetailerService;






@CrossOrigin
@RestController
@RequestMapping("/retailer")
public class Controll {

    @Autowired
     private RetailerService retailerService;

    @Autowired
    private RetServices retServices;

    // @Autowired
    // private LocationService lService;

     
     @GetMapping("/allRetailers")
    public ResponseEntity<List<RetailerDto>> getAllRetailers(){
        return ResponseEntity.ok(retailerService.getAllRetailers());
    }

    @GetMapping("/allRetailers/{id}")
    public ResponseEntity<RetailerDto> getAlCusts(@PathVariable String id){
        return ResponseEntity.ok(retailerService.getRetailerByID(id));
    }

    @GetMapping("/retailersByLocId/{locationId}")
    public ResponseEntity<List<RetailerDto>> getAllRetailersbyLoc(@PathVariable String locationId){
        return ResponseEntity.ok(retailerService.getRetailersByLocation(locationId));
    }

    @PostMapping("/saveRetailer")
    public boolean saveRetailer(@RequestBody Retailer r){
        return retailerService.saveRetailer(r);
    }

    @PutMapping("/updateRetailer")
    public boolean updateRetailer(@RequestBody Retailer r){        
        return retailerService.updateRetailer(r);
    }

    @GetMapping("/getRetailerByName/{name}")
    public ResponseEntity<List<RetailerDto>> getRetailerByName(@PathVariable String name){
        return ResponseEntity.ok(retailerService.getByRetailerName(name));
    }

    @GetMapping("/getRetailerByUserName/{uname}")
    public ResponseEntity<RetailerDto> getRetailerByUserName(@PathVariable String uname){
        return ResponseEntity.ok(retailerService.getByRetailerUserName(uname));
    }
    

    @GetMapping("/getServicesByRetId/{id}")
    public List<Services> getServices(@PathVariable String id){
        return retServices.getServicesByRetId(id);
    }

    // @GetMapping("/getServicesByRetId/{id}")
    // public List<Retailer> getRetailersByLocation(@PathVariable String id){
    //     return lService.getRetailerList(id);
    // }
}
