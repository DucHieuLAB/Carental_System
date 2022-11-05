package com.example.create_entity.dto.Response;

import com.example.create_entity.Entity.ContractDetailEntity;
import com.example.create_entity.Entity.DistrictsEntity;
import com.example.create_entity.Entity.ParkingEntity;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {
    private Long id;
    private String nameCar;
    private String plateNumber;
    private Integer capacity;
    private Date expected_start_date;
    private Date expected_end_date;
    private String Name_Pickup_Parking;
    private DistrictReponse District_Pickup_Parking;
    private String Name_Return_Parking;
    private DistrictReponse District_Return_Parking;

    public ScheduleResponse scheduleResponse(ContractDetailEntity contractDetailEntity) {
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
        return response;
    }


}
