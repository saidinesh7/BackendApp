package com.saloonapp.app.dto.customers;

import org.springframework.stereotype.Component;

import com.saloonapp.app.models.customer.Customer;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class CustomerDTO {

    private String id;
    private String name;
    private String email;
    private String mobile;
    private String age;
    private String gender;
    private String profile_img;

    public CustomerDTO customerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setGender(customer.getGender());
        // customerDTO.setUsername(customer.getUsername());
        // customerDTO.setPassword(customer.getPassword());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setMobile(customer.getMobile());
        customerDTO.setAge(customer.getAge());
        customerDTO.setProfile_img(customer.getProfile_img());
        return customerDTO;
    }
}
