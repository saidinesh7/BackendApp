package com.saloonapp.app.repos.customer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saloonapp.app.models.customer.Customer;

;

@Repository
public interface CustRepository extends JpaRepository<Customer, String>{
    public List<Customer> findAllCustomersByGender( String gender);
    public Customer findCustomerByUsernameAndPassword(String username, String password);
    public Customer findCustomerByUsername(String username);
    public Customer findCustomerByEmail(String email);
    // public int countByUsername(String username);
    public int deleteAllByUsername(String username);
    // public int deleteAllByEmail(String email);
}
