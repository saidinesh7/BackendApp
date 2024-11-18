package com.saloonapp.app.controllers.rah;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saloonapp.app.config.identity.JwtService;
import com.saloonapp.app.models.rah.ServiceStatus;
import com.saloonapp.app.models.rah.TableRAH;
import com.saloonapp.app.services.rah.RAHService;



@RestController
@RequestMapping("/rah")
public class RAHController {

   @Autowired
   RAHService rahService;

   @Autowired
   JwtService jService;

    @GetMapping("/getRequestsByRetailer")
    public ResponseEntity<List<TableRAH>> getRequestsByRetailerId(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok( rahService.getRAHQueueByRetailer(token));
    }
    @PostMapping("/createRequest")
    public ResponseEntity<TableRAH> createRequest(@RequestBody TableRAH tableRAH, @RequestHeader("Authorization") String token) {        
        return ResponseEntity.ok(rahService.createRAH(tableRAH, token));
    }
    @GetMapping("/getAllRequestsByCustomer")
    public ResponseEntity<List<TableRAH>> getRequestByCustId(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(rahService.getRAHByCustomer(token));
        
    }

    @PostMapping("/updateRequestApproval")
    public ResponseEntity<TableRAH> updateRequestApproval(@RequestBody TableRAH updates) {
        return ResponseEntity.ok(rahService.updateApproveOrReject(updates.getRequestId(), updates.getRetId(), updates.isAccepted()));
    }

    @GetMapping("/getCurrentCustomerRequest")
    public ResponseEntity<TableRAH> getCurrentCustomerRequest(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(rahService.getCurrentRequestByCustomer(token));
    }
    
     
//    @Autowired
//    RAHRepo rahRepo;
    // @GetMapping("/getcustByid/{id}")
    // public List<TableRAH> getCust(@PathVariable String id){
    //     return rahService.getRAHByCustomer(id);
    // }

    @PutMapping("/updateServiceStatus/{reqId}/{status}")
    public boolean  updateServiceStatus(@PathVariable String reqId,@PathVariable ServiceStatus status){
        return rahService.updateOngoingStatus(reqId, status);
    }

    @DeleteMapping("/withDrawRequest/{reqId}")
    public boolean  withdrawRequest(@PathVariable String reqId){
        return rahService.withdrawalRequest(reqId);
    }


    @PostMapping("/setmessage")
    public boolean setMessage(@RequestBody TableRAH r){
        return rahService.setRetMessage(r.getRequestId(), r.getRetailerMessage());

    }


    // @GetMapping("/getById/{id}")
    // public TableRAH getRahById(@PathVariable String id){
    //     return rahRepo.findByRequestId(id);
    // }

    @GetMapping("/getRequestByRetIdAndApprovalStatus/{id}")
    public List<TableRAH>  getRequestByRetIdAndApprovalStatus(@PathVariable String id){
        return rahService.getRequestByRetIdAndApprovalStatus(id);
    }
    
    @GetMapping("/getRequestByRetIdAndApprovalStatusAndServiceOngoing/{isAccepted}/{serviceOngoing}")
    public List<TableRAH>  getRequestByRetIdAndApprovalStatus(@RequestHeader("Authorization") String token,@PathVariable boolean isAccepted,@PathVariable String serviceOngoing){
        return rahService.getRequestByRetIdAndApprovalStatusAndServiceOngoing(token, isAccepted, serviceOngoing);
    }

    // @GetMapping("/getRequestsByRetailer")
    // public List<TableRAH> getRequestsByRetailer(@RequestHeader("Authorization") String bearerToken){
    //     String extractedToken=bearerToken.substring(7);
    //      String username=jService.extractUsername(extractedToken);
         


        

    //     return null;
    // }

    @GetMapping("/getAcceptedRequestsByRetailer")
    public List<TableRAH> getAcceptedRequestsByRetailer(@RequestHeader("Authorization") String bearertoken){
        return rahService.getAcceptedRequestsByRetailer(bearertoken);
    }

    


}
