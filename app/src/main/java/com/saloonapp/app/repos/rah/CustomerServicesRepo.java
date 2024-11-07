package com.saloonapp.app.repos.rah;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saloonapp.app.models.rah.CustomerServices;



@Repository
public interface CustomerServicesRepo extends JpaRepository<CustomerServices,String> {

    public List<CustomerServices>  getAllCustomerServicesByCustomerId(String custId);

    
}
