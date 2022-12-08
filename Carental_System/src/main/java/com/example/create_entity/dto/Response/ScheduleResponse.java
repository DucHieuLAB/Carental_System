package com.example.create_entity.dto.Response;

import com.example.create_entity.Entity.*;
import com.example.create_entity.dto.Request.CarImgRequest;
import lombok.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {
    private Long id;
    private Long id_driver;
    private String nameCar;
    private String plateNumber;
    private Integer capacity;
    private Date expected_start_date;
    private Date expected_end_date;
    private String Name_Pickup_Parking;
    private DistrictReponse District_Pickup_Parking;
    private String Name_Return_Parking;
    private DistrictReponse District_Return_Parking;
    private CustomerInfoResponse customerInfoResponse;
    private List<Object> listImg;
    private ContractHadDriverReponse hadDriverResponse;
    private Date real_pick_up_date;
    private Date real_return_date;


    public ScheduleResponse scheduleResponse(ContractDetailEntity contractDetailEntity, ContractHadDriverEntity hadDriverEntity,DistrictsEntity pickup,DistrictsEntity returnup) {
        CustomerInfoResponse infoResponse = new CustomerInfoResponse();
        CarImageEntity carImageEntity = new CarImageEntity();
        ContractHadDriverReponse driverResponse = new ContractHadDriverReponse();
        ScheduleResponse response = new ScheduleResponse();
        response.setDistrict_Pickup_Parking(DistrictReponse.createDistricReponse(contractDetailEntity.getBooking().getPickup_parking().getDistrictsEntity()));
        response.setDistrict_Return_Parking(DistrictReponse.createDistricReponse(contractDetailEntity.getBooking().getReturn_parking().getDistrictsEntity()));
        response.setName_Pickup_Parking(contractDetailEntity.getBooking().getPickup_parking().getName());
        response.setName_Return_Parking(contractDetailEntity.getBooking().getReturn_parking().getName());
        response.setCapacity(contractDetailEntity.getCar().getCapacity());
        response.setPlateNumber(contractDetailEntity.getCar().getPlateNumber());
        response.setExpected_end_date(contractDetailEntity.getBooking().getExpected_end_date());
        response.setExpected_start_date(contractDetailEntity.getBooking().getExpected_start_date());
        response.setNameCar(contractDetailEntity.getCar().getModelName());
        response.setId(contractDetailEntity.getId());
        response.setId_driver(contractDetailEntity.getDriverEntity().getId());
        response.setCustomerInfoResponse(infoResponse.customerInfoResponse(contractDetailEntity.getBooking().getCustomer()));
        response.setListImg(carImageEntity.ResponseImg(contractDetailEntity.getCar().getCarImageEntities()));
        response.setHadDriverResponse(driverResponse.hadDriverReponse(hadDriverEntity,pickup,returnup));
        response.setReal_pick_up_date(contractDetailEntity.getReal_pick_up_date());
        response.setReal_return_date(contractDetailEntity.getReal_return_date());
        return response;
    }

}
