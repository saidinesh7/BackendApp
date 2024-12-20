package com.saloonapp.app.services.rah;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saloonapp.app.config.identity.JwtService;
import com.saloonapp.app.dto.retailers.RetailerDto;
import com.saloonapp.app.models.customer.Customer;
import com.saloonapp.app.models.rah.CustomerServices;
import com.saloonapp.app.models.rah.ServiceStatus;
import com.saloonapp.app.models.rah.TableRAH;
import com.saloonapp.app.models.retailers.Retailer;
import com.saloonapp.app.repos.rah.CustomerServicesRepo;
import com.saloonapp.app.repos.rah.RAHRepo;
import com.saloonapp.app.services.customer.CustomerService;
import com.saloonapp.app.services.retailers.RetailerService;

import jakarta.transaction.Transactional;

@Service
public class RAHService implements RAHServiceInterface {
    
    private static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    // @Autowired
    // private CustomerServices customerSer;

    // @Autowired
    // FeigncustomerClient feigncustomerClient;

    @Autowired
    private RAHRepo rahRepo;

    @Autowired
    private CustomerServicesRepo cRepo;  
    
    @Autowired
    private CustomerService customerService;

    @Autowired
    private RetailerService retailerService;

    @Override
    public List<TableRAH> getRAHQueueByRetailer(String token) {
        RetailerDto retailer = retailerService.getRetailerProfile(token);
        List<TableRAH> rahList = rahRepo.findAllByRetIdAndServiceOngoing(retailer.getRetailerId());
        // returns requests under retaier ID and SrviceOnGoing !=Completed
        return rahList;
    }

    @Override
    public TableRAH createRAH(TableRAH rah, String token) {
        String Id = "REQ_" + UUID.randomUUID().toString();

        Customer customer = customerService.getCustomerProfile(token);
        String custId = customer.getId();
        
        List<TableRAH> prevRequest = rahRepo.findAllByCustId(custId);

        boolean allCompleted = true;
        for (TableRAH tableRAH : prevRequest) {
            if (!tableRAH.getServiceOngoing().equals(ServiceStatus.COMPLETED)) {
                allCompleted = false;
                break;
            }
        }
        if (prevRequest == null || prevRequest.size() == 0 || allCompleted) {
            rah.setRequestId(Id);
            rah.setServiceOngoing(ServiceStatus.UNACCEPTED);
            List<CustomerServices> customerServices=rah.getCustExpectedServices();
            for(int i=0;i<customerServices.size();i++){
                CustomerServices customerSer=new CustomerServices();
                customerSer.setCustomerId(custId);
                customerSer.setDuration(customerServices.get(i).getDuration());
                customerSer.setServiceType(customerServices.get(i).getServiceType());
                customerSer.setImages(customerServices.get(i).getImages());
                customerSer.setRetailerId(customerServices.get(i).getRetailerId());
                customerSer.setServiceCost(customerServices.get(i).getServiceCost());
                customerSer.setServiceName(customerServices.get(i).getServiceName());
                customerSer.setServiceId(customerServices.get(i).getServiceId());

                cRepo.save(customerSer);
            }
            return rahRepo.save(rah);
        }

        throw new RuntimeException("Please Withdraw previous request for requesting for new one.");
    }

    @Override
    public TableRAH updateApproveOrReject(String requestId, String retId, boolean isAccepted) {
        TableRAH request = rahRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request does not exist"));
        request.setAccepted(isAccepted);
        request.setServiceOngoing(ServiceStatus.PENDING);
        return rahRepo.save(request);
    }


 

    public List<TableRAH> getRequestByRetIdAndApprovalStatus(String id){

    
        return rahRepo.findByRetIdAndIsAccepted(id, true);

    }

    public List<TableRAH> getRequestByRetIdAndApprovalStatusAndServiceOngoing(String token,boolean isAccepted,String serviceStatus){
        RetailerDto rDto=retailerService.getRetailerProfile(token);
        String id=rDto.getRetailerId();

         ServiceStatus status;
           switch (serviceStatus) {
            case "COMPLETED":
                status=ServiceStatus.COMPLETED;
                break;
           case "ONGOING":
                status=ServiceStatus.ONGOING;
                break;
           case "UNACCEPTED":
                status=ServiceStatus.UNACCEPTED;
                break;
            default:
                 status=ServiceStatus.PENDING;
                break;
           }
        return rahRepo.findByRetIdAndIsAcceptedAndServiceOngoing(id, isAccepted,status);

    }

    
    @Transactional
    public boolean updateOngoingStatus(String requestId,ServiceStatus newStatus){
        int t=rahRepo.updateOngoingStatus(requestId, newStatus);
        if(t>0)
        {
            return true;
        }
        return false;
    }

    @Transactional
    public boolean withdrawalRequest(String requestId){
         int b=rahRepo.deleteByRequestId(requestId);

         if(b>0){
            return true;
         }
        return false;
        
    }
    @Transactional
    public boolean setRetMessage(String requestId,String message){
        TableRAH a=rahRepo.findByRequestId(requestId);

        if(a.getRequestId() !=null){
            a.setRetailerMessage(message);
            return true;
        }
                return false;
    }

    @Override
    public List<TableRAH> getRAHByCustomer(String token) {
        Customer customer = customerService.getCustomerProfile(token);
        String custId = customer.getId();
         List<TableRAH> rah=rahRepo.findAllByCustId(custId);
        List<CustomerServices> cs=cRepo.getAllCustomerServicesByCustomerId(custId);
        rah.get(0).setCustExpectedServices(cs);

        return rah;

    }

    @Override
    public TableRAH getCurrentRequestByCustomer(String token) {
        Customer customer = customerService.getCustomerProfile(token);
        String custId = customer.getId();
        List<TableRAH> prevList = rahRepo.findAllByCustId(custId);
        if (prevList.size() > 0) {
            TableRAH currentRequest = prevList.stream()
                    .filter(tableRAH -> !tableRAH.getServiceOngoing().equals("COMPLETED"))
                    .findFirst()
                    .orElse(null);
            return currentRequest;
        }
        return null;
    }

    @Override
    public List<TableRAH> getAcceptedRequestsByRetailer(String retToken) {
       RetailerDto rDto=retailerService.getRetailerProfile(retToken);
        return getRequestByRetIdAndApprovalStatus(rDto.getRetailerId());
    }

    
    
    
    

    

}
