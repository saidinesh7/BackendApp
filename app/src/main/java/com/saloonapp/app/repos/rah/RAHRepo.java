package com.saloonapp.app.repos.rah;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.saloonapp.app.models.rah.ServiceStatus;
import com.saloonapp.app.models.rah.TableRAH;





@Repository
public interface RAHRepo extends JpaRepository<TableRAH,String> {

    @Modifying
    @Query("select tr from TableRAH tr where tr.retId=:retId and tr.serviceOngoing=0")
    public List<TableRAH> findAllByRetIdAndServiceOngoing(@Param("retId") String retId);
    public List<TableRAH> findAllByCustId(String custId);

    @Modifying
    @Query("update TableRAH rah set rah.serviceOngoing=:service where rah.requestId=:reqId ")
     public int updateOngoingStatus(@Param("reqId") String requestId,@Param("service") ServiceStatus newStatus);

     public int deleteByRequestId(String id);

     public TableRAH findByRequestId(String id);

     public List<TableRAH>  findByRetIdAndIsAccepted(String retailerId,boolean isAccepted);

     public  TableRAH findByRetId(String id);



}
