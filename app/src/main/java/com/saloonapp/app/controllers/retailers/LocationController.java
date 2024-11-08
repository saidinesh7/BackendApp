package com.saloonapp.app.controllers.retailers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saloonapp.app.models.retailers.Retailer;
import com.saloonapp.app.services.retailers.LocationService;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private  LocationService locationService;

    @GetMapping("/getRetailersByLocId/{id}")
    public List<Retailer>  getRetailersByLocId(@PathVariable String id) {
        return locationService.getRetailerList(id);
    }






}
