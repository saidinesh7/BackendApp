package com.saloonapp.app.services.rah;

import java.util.List;

import org.hibernate.mapping.Table;

import com.saloonapp.app.models.customer.Customer;
import com.saloonapp.app.models.rah.ServiceStatus;
import com.saloonapp.app.models.rah.TableRAH;



public interface RAHServiceInterface {

    public List<TableRAH>  getRAHByCustomer(String custId);//customer should only able to make one request at a time otherwise need to ask wthdrawl of prev reqwst
    public TableRAH  getCurrentRequestByCustomer(String custId);
    public List<TableRAH>  getRAHQueueByRetailer(String retId);
    public TableRAH createRAH(TableRAH rah, String token);
    public TableRAH updateApproveOrReject(String requestId,String retId, boolean isAccepted);//it updates the  status of the request with approved and serviceOngoing should be updated to pending.
   
    public List<TableRAH> getRequestByRetIdAndApprovalStatus(String Retid);//approved list
    public List<TableRAH> getAcceptedRequestsByRetailer(String retToken);//approved list
    public boolean updateOngoingStatus(String requestId,ServiceStatus newStatus);//it updates the  ongoingstatus of the request with ongoing
    public boolean withdrawalRequest(String requestId);
    public boolean setRetMessage(String requestId,String message);
    
}
