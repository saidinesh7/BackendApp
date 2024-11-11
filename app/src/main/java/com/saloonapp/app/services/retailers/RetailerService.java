package com.saloonapp.app.services.retailers;
import java.util.List;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.saloonapp.app.config.identity.CustomUserDetailsService;
import com.saloonapp.app.customexceptions.NotFoundException;
import com.saloonapp.app.dto.retailers.RetailerDto;
import com.saloonapp.app.models.identity.UserCredential;
import com.saloonapp.app.models.retailers.Retailer;
import com.saloonapp.app.models.retailers.Services;
import com.saloonapp.app.repos.retailers.RetailerRepo;
import com.saloonapp.app.repos.retailers.ServicesRepo;







@Service
public class RetailerService {



    

    private static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    @Autowired
    private RetailerRepo retailerRepo;

   
    @Autowired
    private CustomUserDetailsService authService;

    @Autowired
    private RetServices retServices;

    @Autowired
    private ServicesRepo servicesRepo;
    

    public List<RetailerDto> getAllRetailers(){

       return retailerRepo.findAll().stream().
       map(m->{m.setServiceList(retServices.getServicesByRetId(m.getRetailerId()));
        return this.retailerToRetailerDTO(m);}).toList();
    }

    public RetailerDto getRetailerByID(String id){
      
        Retailer r= retailerRepo.getByRetailerId(id);
        
        if(r==null) {
        throw new NotFoundException("Retailer with id"+id+"Not Found ");
        }
       // List<Services> s=servicesFeignClient.getServicesByRetailerId(r.getRetailerId());
        List<Services> s=retServices.getServicesByRetId(r.getRetailerId());
        r.setServiceList(s);
        return this.retailerToRetailerDTO(r);
    }



    public boolean saveRetailer(Retailer r){
        Retailer existingRetailer = retailerRepo.getByRetailerId(r.getRetailerId());
        if (existingRetailer != null) {
            throw new RuntimeException("Customer Already Exists");
        }

        String[] Mandatory_Retailer_Feilds = { r.getRetailerName(), r.getRetailerOwner(), r.getRetailerUsername(), r.getRetailerPass() };
        for (String feild : Mandatory_Retailer_Feilds) {

            if (isNullOrEmpty(feild)) {
                throw new NullPointerException("name, username, password cannot be null");
            }
        }
        r.setRetailerId("RET"+UUID.randomUUID().toString());
        List<Services> sList=r.getServiceList();
        
        for(int i=0;i<sList.size();i++)
        {
            Services s=new Services();
            s.setRetailerId(sList.get(i).getRetailerId());
            s.setDuration(sList.get(i).getDuration());
            s.setImages(sList.get(i).getImages());
            s.setServiceCost(sList.get(i).getServiceCost());
            s.setServiceId(sList.get(i).getServiceId());
            s.setServiceName(sList.get(i).getServiceName());
            s.setServiceType(sList.get(i).getServiceType());
            servicesRepo.save(s);
        }

       Retailer ret= retailerRepo.save(r);
       if(ret.getRetailerId()!=null){
        UserCredential user=new UserCredential(ret.getRetailerId(),ret.getRetailerUsername(),ret.getRetailerEmail(),ret.getRetailerPass(),"RETAILER");
        authService.addUser(user);

        return true;
       }
       else
       return false;
    }

    public boolean updateRetailer(Retailer r)
    {
        Retailer existingRetailer;

        if (r.getRetailerId() != null && !r.getRetailerId().trim().isEmpty()) {
            existingRetailer = retailerRepo.getByRetailerId(r.getRetailerId());
            if(existingRetailer == null){
                   throw  new NotFoundException("Retailer with id"+r.getRetailerId()+"Not Found ");
            }
        }  else {
            throw new RuntimeException("Either id or username must be provided");
        }

        if (r.getRetailerName() != null) {
            existingRetailer.setRetailerName(r.getRetailerName());
        }
        if (r.getRetailerEmail() != null) {
            existingRetailer.setRetailerEmail(r.getRetailerEmail());
        }
        if (r.getRetailerMobile() != null) {
            existingRetailer.setRetailerMobile(r.getRetailerMobile());
        }
        if (r.getRetailerOwner() != null) {
            existingRetailer.setRetailerOwner(r.getRetailerOwner());
        }
        
        if (r.getRetailImage()!= null) {
            existingRetailer.setRetailImage(r.getRetailImage());
        }
        if (r.getRetailerPass() != null) {
            existingRetailer.setRetailerPass(r.getRetailerPass());
        }
        if (r.getRetailerDescription() != null) {
            existingRetailer.setRetailerDescription(r.getRetailerDescription());
        }
        if (r.getIsAvailable() != null) {
            existingRetailer.setIsAvailable(r.getIsAvailable());
        }
        if (r.getReatilerAddress() != null) {
            existingRetailer.setReatilerAddress(r.getReatilerAddress());
        }
        if (r.getNoOfBarbers() != null) {
            existingRetailer.setNoOfBarbers(r.getNoOfBarbers());
        }
        if (r.getTimings() != null) {
            existingRetailer.setTimings(r.getTimings());
        }
        

        if ((r.getRetailerName() == null || r.getRetailerName().trim().isEmpty()) &&
                (r.getRetailerEmail() == null || r.getRetailerEmail().trim().isEmpty()) &&
                (r.getRetailerMobile() == null || r.getRetailerMobile().trim().isEmpty()) &&
                (r.getRetailerOwner() == null || r.getRetailerOwner().trim().isEmpty()) &&
                (r.getRetailImage() == null || r.getRetailImage().length==0) &&
                (r.getRetailerPass() == null || r.getRetailerPass().trim().isEmpty()) &&
                (r.getRetailerDescription() == null || r.getRetailerDescription().trim().isEmpty())&&
                (r.getIsAvailable() == null || r.getIsAvailable().trim().isEmpty())&&
                (r.getReatilerAddress() == null || r.getReatilerAddress().trim().isEmpty())&&
                (r.getNoOfBarbers() == null || r.getNoOfBarbers().trim().isEmpty())&&
                (r.getTimings() == null || r.getTimings().trim().isEmpty())


        ) {
            throw new RuntimeException("Atleast one value (other than Id or Username) to be provided for update");
        }

        
        Retailer retailer = retailerRepo.save(existingRetailer);
        if (retailer.getRetailerId() != null) {
            return true;
        }
        return false;
    }
    
    public List<RetailerDto> getRetailersByLocation(String str){
        List<Retailer> r= retailerRepo.getByRetailerLocationId(str);
        
         return r.stream().map(m->this.retailerToRetailerDTO(m)).toList();
    }

    public List<RetailerDto> getByRetailerName(String name){
        List<Retailer> r=retailerRepo.getByRetailerName(name);
        return r.stream().map(m->this.retailerToRetailerDTO(m)).toList();
    }

    public RetailerDto getByRetailerUserName(String name){
        return this.retailerToRetailerDTO(retailerRepo.getByRetailerUsername(name));
    }

   


    public RetailerDto retailerToRetailerDTO(Retailer r){
        RetailerDto retailerDto= new RetailerDto();
        retailerDto.setRetailerId(r.getRetailerId());
        retailerDto.setRetailerName(r.getRetailerName());
        retailerDto.setRetailerDescription(r.getRetailerDescription());
        retailerDto.setReatilerAddress(r.getReatilerAddress());
        retailerDto.setRetailerLocationId(r.getRetailerLocationId());
        retailerDto.setNoOfBarbers(r.getNoOfBarbers());
        retailerDto.setTimings(r.getTimings());
        retailerDto.setIsAvailable(r.getIsAvailable());
        retailerDto.setNoOfBarbers(r.getNoOfBarbers());
        retailerDto.setRetailImage(r.getRetailImage());
        retailerDto.setRetailerMobile(r.getRetailerMobile());
        retailerDto.setRating(r.getRating());
        retailerDto.setRetailerOwner(r.getRetailerOwner());
        retailerDto.setRetailerEmail(r.getRetailerEmail());
        retailerDto.setServiceList(r.getServiceList());
       return retailerDto;
    }
}
