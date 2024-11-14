package com.saloonapp.app.controllers.customer;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.saloonapp.app.dto.customers.CustomerDTO;
import com.saloonapp.app.models.customer.Customer;
import com.saloonapp.app.services.customer.CustomerService;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping(path = "/customer")
@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    
    @Autowired
    private CustomerDTO customerDTO;

    @GetMapping("/getAllCustomers")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return ResponseEntity.ok( customerService.getAllCustomers().stream().map(m -> customerDTO.customerToCustomerDTO(m)).toList());
    }

    @GetMapping("/getAllCustomersByGender/{gender}")
    public List<CustomerDTO> getAllCustomersByGender(@PathVariable String gender) {
        return customerService.getAllCustomersByGender(gender).stream().map(m -> customerDTO.customerToCustomerDTO(m))
                .toList();
    }

    @GetMapping("/login/{username}/{password}")
    public CustomerDTO login(@PathVariable String username, @PathVariable String password) {
        return customerDTO.customerToCustomerDTO(customerService.getCustomersByUserameAndPassword(username, password));
    }

    @PostMapping("/Signup")
    public boolean signUp(@RequestBody Customer c) {
        return customerService.createCustomer(c);
    }
    @PostMapping("/uploadCustDP")
    public boolean uploadCustDP( @RequestParam("file") MultipartFile file , @RequestParam("username") String username) {
        return customerService.uploadCustDP(file, username);
    }


    @GetMapping("/deleteCustomerByID/{id}")
    public boolean deleteCustomerByID(@PathVariable String id) {
        return customerService.deleteCustomerByID(id);
    }

    @GetMapping("/deleteCustomerByUsername/{username}")
    public boolean deleteCustomerByUsername(@PathVariable String username) {
        return customerService.deleteCustomerByUsername(username);
    }

    @GetMapping("/getCustomerById/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable String id) {
        return ResponseEntity.ok(customerDTO.customerToCustomerDTO(customerService.getCustomerById(id)));
    }
    @GetMapping("/getCustomerProfile")
    public ResponseEntity<CustomerDTO> getCustomerProfile(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(customerDTO.customerToCustomerDTO(customerService.getCustomerProfile(token)));
    }

    @GetMapping("/getCustomerByUsername/{username}")
    public CustomerDTO getCustomerByUsername(@PathVariable String username) {
        return customerDTO.customerToCustomerDTO(customerService.getCustomerByUsername(username));
    }

    @GetMapping("/updateCustomerPassword/{newpassword}")
    public boolean updateCustomerPassword(@PathVariable String newpassword, @RequestHeader("Authorization") String token) {
        return customerService.updateCustomerPassword(token, newpassword);
    }

    @PostMapping("/updateCustomer")
    public boolean updateCustomer(@RequestBody Customer updatedCustomer, @RequestHeader("Authorization") String token) {
        return customerService.updateCustomerData(updatedCustomer, token);
    }

}
