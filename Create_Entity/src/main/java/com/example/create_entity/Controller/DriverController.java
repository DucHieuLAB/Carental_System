package com.example.create_entity.Controller;
import com.example.create_entity.Entity.DriverEntity;
import com.example.create_entity.Repository.DriverRepository;
import com.example.create_entity.dto.Request.DriverInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DriverController {

    @Autowired
    DriverRepository driverRepository;


    @RequestMapping(value = "/ListDriver",method = RequestMethod.GET)
    public ResponseEntity<?>  RegisAccountDriver(){
      List<DriverEntity> driverEntities =  driverRepository.findAll();
        List<DriverInfoRequest> driverInfoRequests = new ArrayList<>();
        DriverInfoRequest infoRequest = new DriverInfoRequest() ;

        for (DriverEntity driverEntity: driverEntities) {
       infoRequest.setDriverNumberLicense(driverEntity.getDriver_Number_License());
       infoRequest.setYearExperience(driverEntity.getYear_Experience());
       infoRequest.setStatus(1);
       driverInfoRequests.add(infoRequest);
        }





      if(!driverInfoRequests.isEmpty()){
          return new ResponseEntity<>(driverInfoRequests,HttpStatus.OK);
      }else {
          return null;
      }
    }
}
