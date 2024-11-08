package com.saloonapp.app.controllers.retailers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.saloonapp.app.models.retailers.Services;
import com.saloonapp.app.services.retailers.RetServices;




@CrossOrigin(origins = "*",allowCredentials = "true",allowedHeaders = "*")
@RestController
@RequestMapping("/retailerservices")
public class ServicesController {

    @Autowired
    private RetServices retServices;

    @GetMapping("/getServicesByRetId/{retId}")
    public List<Services> getServicesByRetId(@PathVariable String retId) {
        return retServices.getServicesByRetId(retId);
    }


}
