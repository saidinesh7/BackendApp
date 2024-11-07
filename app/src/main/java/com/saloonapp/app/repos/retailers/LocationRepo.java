package com.saloonapp.app.repos.retailers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saloonapp.app.models.retailers.Location;



@Repository
public interface LocationRepo extends JpaRepository<Location,String>{


}
