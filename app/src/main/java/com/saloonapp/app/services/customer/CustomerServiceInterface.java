package com.saloonapp.app.services.customer;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.saloonapp.app.models.customer.Customer;



public interface CustomerServiceInterface {

    // Create Customer
    public boolean createCustomer(Customer c);
    public boolean uploadCustDP(MultipartFile file, String username);
    // Delete Customer
    public boolean deleteCustomerByID(String id);
    public boolean deleteCustomerByUsername(String username);
    // Get Customer
    public Customer getCustomerById(String id);
    public Customer getCustomerByUsername(String username);
    // public Customer getCustomerByEmail(String email);
    public Customer getCustomersByUserameAndPassword(String username, String password);
    // Get All Customers
    public List<Customer> getAllCustomers();
    public List<Customer> getAllCustomersByGender(String gender);

    // Update Customer data
    // public boolean updateCustomerName(String id, String newName);
    // public boolean updateCustomerEmail(String id, String newEmail);
    // public boolean updateCustomerMobile(String id, String newMobile);
    // public boolean updateCustomerAge(String id, String newAge);
    // public boolean updateCustomerGender(String id, String newGender);
    // public boolean updateCustomerProfileImg(String id, String newProfileImg);
    public boolean updateCustomerPassword(String username, String newPassword);
    public boolean updateCustomerData(Customer upadtedCustomer);


}
