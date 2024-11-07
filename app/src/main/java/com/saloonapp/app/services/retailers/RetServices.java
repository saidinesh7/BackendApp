package com.saloonapp.app.services.retailers;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saloonapp.app.models.retailers.Services;
import com.saloonapp.app.repos.retailers.ServicesRepo;



@Service
public class RetServices {

    @Autowired
    private ServicesRepo servicesRepo;

    public List<Services> getServicesByRetId(String retId){
        return servicesRepo.getServicesByRetailerId(retId);
    }

}
