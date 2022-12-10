package com.example.create_entity;

import com.example.create_entity.Entity.CarEntity;
import com.example.create_entity.Repository.CarRepository;
import com.example.create_entity.Service.CarServiceImpl;
import com.example.create_entity.dto.Request.CarRequest;
import com.example.create_entity.dto.Response.ResponseVo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CarServiceTest {
    @Autowired
    CarServiceImpl carService;

    @Autowired
    CarRepository carRepository;

    @BeforeAll
    public static void setUp() {

    }

    // test add car
    @DisplayName("Add car valid")
    @Test
    @Order(1)
    public void addCarTest() {
        CarRequest carRequest = new CarRequest();
        carRequest.setModelName("Toyota Vios");
        carRequest.setBrandId(1);
        carRequest.setYearOfManufacture(2018);
        carRequest.setRentalPrice(300000);
        carRequest.setDepositAmount(500000);
        carRequest.setPlateNumber("30A-123.456");
        carRequest.setCapacity(5);
        carRequest.setFuel("Xăng");
        carRequest.setGears("gears");
        carRequest.setColor("Trắng");
        carRequest.setStatus(1);
        carRequest.setDescription("Toyota Vios");
        carRequest.setLicenseId(2);
        carRequest.setParkingId(1);
        List<String> img = new ArrayList<>();
        img.add("https://static.danhgiaxe.com/data/201525/the-aventadors-aggressive-lines-and-stealth-fighter-like-edges-make-for-a-menacing-beauty-thats-perfect-for-lambos-attention-hoarding-ethos_2324.jpg");
        ResponseEntity<?> responseEntity = carService.add(carRequest);
        CarEntity carEntity = carRepository.findCarEntityByPlateNumber("30A-123.456");
        Assertions.assertNotNull(carEntity);
    }

    // test add car invalid parking
    @DisplayName("Add car invalid parking")
    @Test
    @Order(2)
    public void addWrongParking() {
        CarRequest carRequest = new CarRequest();
        carRequest.setModelName("Toyota Vios");
        carRequest.setBrandId(1);
        carRequest.setYearOfManufacture(2018);
        carRequest.setRentalPrice(300000);
        carRequest.setDepositAmount(500000);
        carRequest.setPlateNumber("30A-123.457");
        carRequest.setCapacity(5);
        carRequest.setFuel("Xăng");
        carRequest.setGears("gears");
        carRequest.setColor("Trắng");
        carRequest.setStatus(1);
        carRequest.setDescription("Toyota Vios");
        carRequest.setLicenseId(2);
        //invalid parrking
        carRequest.setParkingId(5);
        List<String> img = new ArrayList<>();
        img.add("https://static.danhgiaxe.com/data/201525/the-aventadors-aggressive-lines-and-stealth-fighter-like-edges-make-for-a-menacing-beauty-thats-perfect-for-lambos-attention-hoarding-ethos_2324.jpg");
        ResponseEntity<?> responseEntity = carService.add(carRequest);
        CarEntity carEntity = carRepository.findCarEntityByPlateNumber("30A-123.457");
        Assertions.assertNull(carEntity);
    }

    // test add car invalid lycense
    @DisplayName("Add car invalid lycense")
    @Test
    @Order(3)
    public void addWrongLycense() {
        CarRequest carRequest = new CarRequest();
        carRequest.setModelName("Toyota Vios");
        carRequest.setBrandId(1);
        carRequest.setYearOfManufacture(2018);
        carRequest.setRentalPrice(300000);
        carRequest.setDepositAmount(500000);
        carRequest.setPlateNumber("30A-123.457");
        carRequest.setCapacity(5);
        carRequest.setFuel("Xăng");
        carRequest.setGears("gears");
        carRequest.setColor("Trắng");
        carRequest.setStatus(1);
        carRequest.setDescription("Toyota Vios");
        // invalid licenseId
        carRequest.setLicenseId(0);
        carRequest.setParkingId(1);
        List<String> img = new ArrayList<>();
        img.add("https://static.danhgiaxe.com/data/201525/the-aventadors-aggressive-lines-and-stealth-fighter-like-edges-make-for-a-menacing-beauty-thats-perfect-for-lambos-attention-hoarding-ethos_2324.jpg");
        ResponseEntity<?> responseEntity = carService.add(carRequest);
        CarEntity carEntity = carRepository.findCarEntityByPlateNumber("30A-123.457");
        Assertions.assertNull(carEntity);
    }

    // test getlist capacity
    @DisplayName("Get list capacity")
    @Test
    public void getListCapacityTest() {
        List<Integer> result = carRepository.getListCapacity();
        Assertions.assertEquals(result.size(), 1);
    }

    // test update car
    @DisplayName("Test Update Car ")
    @Test
    public void updateCarTest() {
        CarRequest carRequest = new CarRequest();
        carRequest.setModelName("Toyota Vios");
        carRequest.setBrandId(1);
        carRequest.setYearOfManufacture(2020);
        carRequest.setRentalPrice(300000);
        carRequest.setDepositAmount(500000);
        carRequest.setPlateNumber("30A-123.459");
        carRequest.setCapacity(5);
        carRequest.setFuel("Xăng");
        carRequest.setGears("gears");
        //update color from write => red
        carRequest.setColor("Red");
        carRequest.setStatus(1);
        carRequest.setDescription("Toyota Vios");
        carRequest.setLicenseId(2);
        carRequest.setParkingId(1);
        List<String> img = new ArrayList<>();
        img.add("https://static.danhgiaxe.com/data/201525/the-aventadors-aggressive-lines-and-stealth-fighter-like-edges-make-for-a-menacing-beauty-thats-perfect-for-lambos-attention-hoarding-ethos_2324.jpg");
        CarEntity carEntity1 = carRepository.findCarEntityByPlateNumber("30A-123.457");
        if (!ObjectUtils.isEmpty(carEntity1)){
            carRequest.setId(carEntity1.getId());
            ResponseEntity<?> responseEntity = carService.update(carRequest);
            CarEntity carEntity = carRepository.findCarEntityByPlateNumber("30A-123.459");
            // check not null
            Assertions.assertNotNull(carEntity);
            // check update
            Assertions.assertEquals(carEntity.getModelName(), (carRequest.getModelName()));
            Assertions.assertEquals(carEntity.getBrand().getId(), (carRequest.getBrandId()));
            Assertions.assertEquals(carEntity.getRentalPrice(),carRequest.getRentalPrice());
            Assertions.assertEquals(carEntity.getDepositAmount(),carRequest.getDepositAmount());
            Assertions.assertEquals(carEntity.getYearOfManufacture(),carRequest.getYearOfManufacture());
            Assertions.assertEquals(carEntity.getLicenseTypeEntity().getID(),carRequest.getLicenseId());
            Assertions.assertEquals(carEntity.getParking().getId(),carRequest.getParkingId());
        }

    }

    // test delete car
    @DisplayName("Test Delete Car ")
    @Test
    public void deleteCarTest() {

    }

    // test getlist car search by capacity
    @DisplayName("Get list car search by capacity")
    @Test
    public void getListCarSearchByCapacityTest() {

    }

    // test getlist car search by parkingId
    @DisplayName("Get list car search by parkingId")
    @Test
    public void getListCarSearchByParkingIdTest() {

    }

    // test getlist car search by parkingId
    @DisplayName("Get list car search by modelName")
    @Test
    public void getListCarSearchByModelNameTest() {

    }

    // test get Car by plateNumber
    @DisplayName("Get car by plateNumber")
    @Test
    public void getCarByPlateNumberTest() {

    }

    // get list driver test
    @DisplayName("Get list driver test")
    @Test
    public void getListDriverValidForContractTest() {

    }

    // search car for contract self driver type
    @DisplayName("Search car for contract self driver type")
    @Test
    public void searchCarNoDriverTest() {

    }

    // search car for contract had driver type
    @DisplayName("Search car for contract had driver type")
    @Test
    public void searchCarHadDriverTest() {

    }
}
