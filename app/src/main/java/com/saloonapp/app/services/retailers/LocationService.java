package com.saloonapp.app.services.retailers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saloonapp.app.models.retailers.Retailer;
import com.saloonapp.app.repos.retailers.RetailerRepo;






@Service
public class LocationService {


    @Autowired
    private RetailerRepo repo;


    public List<Retailer>  getRetailerList(String id) {
      
      return repo.getByRetailerLocationId(id);
    }



}
