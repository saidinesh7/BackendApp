package com.saloonapp.app.repos.retailers;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saloonapp.app.models.retailers.Retailer;





@Repository
public interface RetailerRepo extends JpaRepository<Retailer,String> {


    public Retailer getByRetailerId(String id);
    public List<Retailer> getByRetailerLocationId(String locId);
    public List<Retailer> getByRetailerName(String retName);
    public Retailer getByRetailerUsername(String uname);
    

}
