package com.saloonapp.app.services.customer;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import com.saloonapp.app.config.identity.CustomUserDetailsService;
import com.saloonapp.app.config.identity.JwtService;
import com.saloonapp.app.models.customer.Customer;
import com.saloonapp.app.models.identity.UserCredential;
import com.saloonapp.app.repos.customer.CustRepository;


import jakarta.transaction.Transactional;

@Service
public class CustomerService implements CustomerServiceInterface {
    private static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService custService;

    @Autowired
    private CustRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> getAllCustomersByGender(String gender) {
        return customerRepository.findAllCustomersByGender(gender);
    }

    @Override
    public Customer getCustomersByUserameAndPassword(String username, String password) {
        return customerRepository.findCustomerByUsernameAndPassword(username, password);

    }

    @Override
    public boolean createCustomer(Customer c) {
        Customer existingCustomer = customerRepository.findCustomerByUsername(c.getUsername());
        if (existingCustomer != null && existingCustomer.getId() != null) {
            throw new RuntimeException("Customer Already Exists");
        }
        if (c.getGender() == null) {
            // Handle the case where gender is null, e.g., throw an exception or return
            // false
            throw new NullPointerException("Gender cannot be null");
        }
        String[] Mandatory_Cust_Feilds = { c.getName(), c.getGender(), c.getUsername(), c.getPassword() };
        for (String feild : Mandatory_Cust_Feilds) {

            if (isNullOrEmpty(feild)) {
                throw new NullPointerException("name, gender, username, password cannot be null");
            }
        }
        String Id = "CUS_" + UUID.randomUUID().toString();
        c.setId(Id);
        Customer cust = customerRepository.save(c);
        if (cust.getId() != null) {
            UserCredential  userCredential = new UserCredential(
                cust.getId(),
                cust.getUsername(),
                cust.getEmail(),
                cust.getPassword(),
                "CUSTOMER"
            );
            custService.addUser(userCredential);
            return true;
        }
        return false;
    }

    @Override
    public boolean uploadCustDP(MultipartFile file, String username){
        Customer existingCustomer = customerRepository.findCustomerByUsername(username);
        if (existingCustomer != null && existingCustomer.getId() != null) {
            try {
                existingCustomer.setProfile_img(file.getBytes());
                customerRepository.save(existingCustomer);
                return true;
            } catch (IOException e) {
                System.err.println("Error in img save: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException(e);
            }

        }
        else throw new RuntimeException("Customer with username: "+username+ " does not exist.");

    }

    @Override
    public boolean deleteCustomerByID(String id) {
        Optional<Customer> cust = customerRepository.findById(id);
        if (cust.isPresent()) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean deleteCustomerByUsername(String username) {
        int deletedCount = customerRepository.deleteAllByUsername(username);
        // customerRepository.deleteAllByEmail(username);
        if (deletedCount > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Customer getCustomerById(String id) {
        System.out.println("id: " + id);
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer doen't exist"));
        // return customerRepository.findById(id).orElseThrow();
    }
    @Override
    public Customer getCustomerProfile(String token) {
        token = token.substring(7);
        System.out.println("Token: " + token);
        String customerName = jwtService.extractUsername(token);
        return getCustomerByUsername(customerName);
    }

    @Override
    public Customer getCustomerByUsername(String username) {
        Customer cust = customerRepository.findCustomerByUsername(username);
        if (cust == null || isNullOrEmpty(cust.getId())) {
            throw new RuntimeException("Customer doen't exist");
        }
        return cust;
    }
    // @Override
    // public Customer getCustomerByEmail(String email) {
    // return customerRepository.findAllCustomerByEmail(email);
    // }
    // @Override
    // public boolean updateCustomerName(String id, String newName) {
    // Customer customer = getCustomerById(id);
    // customer.setName(newName);
    // return createCustomer(customer);

    // }
    
    @Override
    public boolean updateCustomerPassword(String token, String newPassword) {
        Customer customer = getCustomerProfile(token);
        // updating userCrendentials before updating user table
        custService.updateUserPassword(customer.getUsername(), customer.getPassword());

        customer.setPassword(newPassword);
        System.out.println(customerRepository.save(customer));
        return true;

        // return createCustomer(customer);

    }

    @Override
    public boolean updateCustomerData(Customer updateCustomer, String token) {
        Customer customer = getCustomerProfile(token);

        Customer existingCustomer;

        if (updateCustomer.getId() != null && !updateCustomer.getId().trim().isEmpty()) {
            existingCustomer = customerRepository.findById(updateCustomer.getId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
        } else if (updateCustomer.getUsername() != null && !updateCustomer.getUsername().trim().isEmpty()) {

            existingCustomer = customerRepository.findCustomerByUsername(updateCustomer.getUsername());
            if (existingCustomer == null || isNullOrEmpty(existingCustomer.getId())) {

                throw new RuntimeException("Customer not found");
            }
        }else if(!isNullOrEmpty(customer.getId())){
            existingCustomer = customer;

        } else {
            throw new RuntimeException("Either id or username must be provided");
        }
        String[] Mandatory_Cust_Feilds = { updateCustomer.getName(), updateCustomer.getGender(),
                updateCustomer.getPassword(), updateCustomer.getEmail(),
                // updateCustomer.getProfile_img(),
                updateCustomer.getMobile(), updateCustomer.getAge() };
        int count = 0;
        for (String feild : Mandatory_Cust_Feilds) {

            if (!isNullOrEmpty(feild)) {
                count++;
            }
        }
        if (count == 0 ) {
            throw new RuntimeException("Atleast one value (other than Id or Username) to be provided for update");
        }

        if (updateCustomer.getName() != null) {
            existingCustomer.setName(updateCustomer.getName());
        }
        if (updateCustomer.getEmail() != null) {
            existingCustomer.setEmail(updateCustomer.getEmail());
        }
        if (updateCustomer.getMobile() != null) {
            existingCustomer.setMobile(updateCustomer.getMobile());
        }
        if (updateCustomer.getAge() != null) {
            existingCustomer.setAge(updateCustomer.getAge());
        }
        if (updateCustomer.getGender() != null) {
            existingCustomer.setGender(updateCustomer.getGender());
        }
        if (updateCustomer.getProfile_img() != null) {
            existingCustomer.setProfile_img(updateCustomer.getProfile_img());
        }
        if (updateCustomer.getPassword() != null) {
            custService.updateUserPassword(updateCustomer.getUsername(), updateCustomer.getPassword());
            existingCustomer.setPassword(updateCustomer.getPassword());
        }

        // if ((updateCustomer.getName() == null || updateCustomer.getName().trim().isEmpty()) &&
        //         (updateCustomer.getEmail() == null || updateCustomer.getEmail().trim().isEmpty()) &&
        //         (updateCustomer.getMobile() == null || updateCustomer.getMobile().trim().isEmpty()) &&
        //         (updateCustomer.getAge() == null || updateCustomer.getAge().trim().isEmpty()) &&
        //         (updateCustomer.getGender() == null || updateCustomer.getGender().trim().isEmpty()) &&
        //         (updateCustomer.getProfile_img() == null || updateCustomer.getProfile_img().trim().isEmpty()) &&
        //         (updateCustomer.getPassword() == null || updateCustomer.getPassword().trim().isEmpty())

        // ) {
        //     throw new RuntimeException("Atleast one value (other than Id or Username) to be provided for update");
        // }

        // return createCustomer(existingCustomer);
        Customer cust = customerRepository.save(existingCustomer);
        if (cust.getId() != null) {
            return true;
        }
        return false;
    }

}