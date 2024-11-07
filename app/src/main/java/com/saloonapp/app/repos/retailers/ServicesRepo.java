package com.saloonapp.app.repos.retailers;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saloonapp.app.models.retailers.Services;




@Repository
public interface ServicesRepo extends JpaRepository<Services,String>{

    public List<Services> getServicesByRetailerId(String retId);


}
