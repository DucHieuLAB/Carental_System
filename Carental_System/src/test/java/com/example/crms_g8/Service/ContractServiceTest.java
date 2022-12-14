package com.example.crms_g8.Service;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContractServiceTest {
//    @Autowired
//    ContractServiceImpl contractService;
//
//    @Autowired
//    ContractRepository contractRepository;
//
//    @Autowired
//    ContractDetailRepository contractDetailRepository;
//
//    @Autowired
//    ContractHadDriverRepository contractHadDriverRepository;
//
//    @Autowired
//    PaymentsRepository paymentsRepository;
//
//    @Autowired
//    CarRepository carRepository;
//
//    @DisplayName("Test Find Contract By CustomerID , ExceptedStartDate, ExceptedEndDate")
//    @Test
//    @Order(2)
//    public void getListExsitContract(){
//        String sDate="17/12/2022";
//        String eDate="18/12/2022";
//        Date date1= null;
//        try {
//            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
//            Date date2= new SimpleDateFormat ("dd/MM/yyyy").parse(eDate);
//            long customerId = 1;
//            List<ContractEntity> contractEntities = contractRepository.findByCustomerIDAndExpectedStartDateAndExpectedEndDate(customerId,date1,date2);
//            Assertions.assertEquals(contractEntities.size(),1);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @DisplayName("Test Get Contract By Customer ID , StartDate , EndDate , type")
//    @Test
//    @Order(3)
//    public void getContract(){
//        String sDate="17/12/2022";
//        String eDate="18/12/2022";
//        Date date1= null;
//        try {
//            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
//            Date date2= new SimpleDateFormat ("dd/MM/yyyy").parse(eDate);
//            long customerId = 1;
//            ContractEntity contractEntities = contractRepository.getByCustomerIdAndExpectStartDateAndExpectEndDateAndType(customerId,date1,date2,true);
//            Assertions.assertNotNull(contractEntities);
//        } catch (ParseException e) {
//            Assertions.assertNull(e);
//        }
//    }
//    @Order(1)
//    @DisplayName("Test Create Contract Seft Driver")
//    @Test
//    public void createContractTest() {
//        try {
//            String sDate="17/12/2022";
//            String eDate="18/12/2022";
//            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
//            Date date2= new SimpleDateFormat ("dd/MM/yyyy").parse(eDate);
//            ContractRequest contractRequest = new ContractRequest();
//            contractRequest.setPickupParkingId(1);
//            contractRequest.setReturnParkingId(1);
//            contractRequest.setExpectedStartDate(date1);
//            contractRequest.setExpectedEndDate(date2);
//            contractRequest.setNote("Kiểm tra xe trước khi lấy");
//            contractRequest.setHad_driver(false);
//            contractRequest.setCustomerId(1);
//            contractRequest.setStatus(2);
//            List<String> plateNumbers = new ArrayList<>();
//            plateNumbers.add("29A-258.45");
//            contractRequest.setListCarPlateNumber(plateNumbers);
//            // add contract
//            contractService.add(contractRequest);
//            // get Contract From Database
//            ContractEntity contractEntity = contractRepository.getByCustomerIdAndExpectStartDateAndExpectEndDateAndType(contractRequest.getCustomerId(),contractRequest.getExpectedStartDate(),contractRequest.getExpectedEndDate(),contractRequest.isHad_driver());
//            // check contract from database
//            Assertions.assertNotNull(contractEntity);
//            // check startDate, endDate
//            Assertions.assertEquals(contractRequest.getExpectedStartDate(),contractEntity.getExpected_start_date());
//            Assertions.assertEquals(contractRequest.getExpectedEndDate(),contractEntity.getExpected_end_date());
//            // check customer
//            Assertions.assertEquals(contractRequest.getCustomerId(),contractEntity.getCustomer().getID());
//            // check car
//            List<ContractDetailEntity> contractDetailEntity = contractDetailRepository.getListContractDetailEntitiesByContractId(contractEntity.getId());
//            // check number of car
//            Assertions.assertEquals(contractDetailEntity.size(),contractRequest.getListCarPlateNumber().size());
//            // check Price
//        }catch (Exception e){
//            Assertions.assertNull(e);
//        }
//    }
//    @Order(4)
//    @DisplayName("Test Create Contract Had Driver")
//    @Test
//    public void createContractHadDriverTest() {
//       try {
//           ContractRequest contractRequest = new ContractRequest();
//           // add contract
//           String sDate="17/01/2023";
//           String eDate="18/01/2023";
//           Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
//           Date date2= new SimpleDateFormat ("dd/MM/yyyy").parse(eDate);
//           contractRequest.setPickupParkingId(1);
//           contractRequest.setReturnParkingId(1);
//           contractRequest.setExpectedStartDate(date1);
//           contractRequest.setExpectedEndDate(date2);
//           contractRequest.setNote("Kiểm tra xe trước khi lấy");
//           contractRequest.setHad_driver(true);
//           contractRequest.setCustomerId(1);
//           contractRequest.setStatus(1);
//           List<String> plateNumbers = new ArrayList<>();
//           plateNumbers.add("29A-258.45");
//           contractRequest.setListCarPlateNumber(plateNumbers);
//           contractRequest.setOneWay(false);
//           contractRequest.setPickUpAddress("HH1C Linh Đàm, Hoàng Mai, Hà Nội");
//           contractRequest.setReturnAddress("HH1C Linh Đàm, Hoàng Mai, Hà Nội");
//           contractRequest.setDistricPickUpAddress(new DistricRequest(null,"Linh Đàm","Hoàng Mai","Thành phố Hà Nội"));
//           contractRequest.setDistricReturnAddress(new DistricRequest(null,"Linh Đàm","Hoàng Mai","Thành phố Hà Nội"));
//           contractService.add(contractRequest);
//           // get Contract By
//           ContractEntity contractEntity = contractRepository.getByCustomerIdAndExpectStartDateAndExpectEndDateAndType(contractRequest.getCustomerId(),contractRequest.getExpectedStartDate(),contractRequest.getExpectedEndDate(),contractRequest.isHad_driver());
//           // check contract from database
//           Assertions.assertNotNull(contractEntity);
//           // check startDate, endDate
//           Assertions.assertEquals(contractRequest.getExpectedStartDate(),contractEntity.getExpected_start_date());
//           Assertions.assertEquals(contractRequest.getExpectedEndDate(),contractEntity.getExpected_end_date());
//           // check customer
//           Assertions.assertEquals(contractRequest.getCustomerId(),contractEntity.getCustomer().getID());
//           // check quantity
//           Assertions.assertEquals(contractEntity.getQuantity(),contractRequest.getListCarPlateNumber().size());
//           // check car
//           List<ContractDetailEntity> contractDetailEntity = contractDetailRepository.getListContractDetailEntitiesByContractId(contractEntity.getId());
//           // check number of car
//           Assertions.assertEquals(contractDetailEntity.size(),contractRequest.getListCarPlateNumber().size());
//           // check pickup Address
//           ContractHadDriverEntity entity = contractHadDriverRepository.getByContractID(contractEntity.getId());
//           Assertions.assertNotNull(entity);
//           Assertions.assertEquals(entity.getPickup_address(),contractRequest.getPickUpAddress());
//           Assertions.assertEquals(entity.getReturn_address(),contractRequest.getReturnAddress());
//       }catch (Exception e){
//           Assertions.assertNull(e);
//       }
//
//    }
//
//    @DisplayName("Test Get Contract Detail")
//    @Test
//    @Order(6)
//    public void getContractDetail() {
//        long contractId = 71;
//        ContractEntity contractEntity = contractRepository.FindByID(contractId);
//        Assertions.assertNotNull(contractEntity);
//        List<ContractDetailEntity> contractDetailEntities = contractDetailRepository.getListContractDetailEntitiesByContractId(contractId);
//        Assertions.assertNotNull(contractDetailEntities);
//        if (contractEntity.isHad_driver()){
//            ContractHadDriverEntity entity = contractHadDriverRepository.getByContractID(contractId);
//            Assertions.assertNotNull(entity);
//        }
//        ContractResponse response = ContractEntity.convertToContractResponse(contractEntity);
//        // check Response Start Date
//        Assertions.assertEquals(response.getExpectedStartDate(),contractEntity.getExpected_start_date());
//        // check Response EndDate
//        Assertions.assertEquals(response.getExpectedEndDate(),contractEntity.getExpected_end_date());
//        // check customer
//        Assertions.assertEquals(response.getCustomerId(),contractEntity.getCustomer().getID());
//        // check Type
//        Assertions.assertEquals(response.isHad_driver(),contractEntity.isHad_driver());
//    }
//
//    @DisplayName("Test Get List Contract Detail By Customer")
//    @Test
//    @Order(7)
//    public void getListContractByCustomerID() {
//        List<ContractEntity> contractEntities = contractRepository.getByCustomerId(1);
//        Assertions.assertEquals(contractEntities.size(),9);
//    }
//
//    @DisplayName("Test Update Driver And RealPrice")
//    @Test
//    @Order(8)
//    public void updateDriverAndRealPriceTest() {
//        long contractId = 82;
//        long contractDetailId = 63;
//        long driverId = 1;
//        ContractDriverRealPriceRequest contractDriverRealPriceRequest = new ContractDriverRealPriceRequest();
//        contractDriverRealPriceRequest.setReal_price(1560000);
//        contractDriverRealPriceRequest.setContractId(contractId);
//        List<ListContractDetailDriverRequest> listContractDetailDriverRequests = new ArrayList<>();
//
//        listContractDetailDriverRequests.add(new ListContractDetailDriverRequest(contractDetailId,driverId));
//        contractDriverRealPriceRequest.setListContractDetailDriverRequests(listContractDetailDriverRequests);
//        try {
//            contractService.updateDriverAndRealPrice(contractDriverRealPriceRequest);
//            ContractEntity contractEntity = contractRepository.FindByID(contractId);
//            Assertions.assertNotNull(contractEntity);
//            Assertions.assertEquals(contractEntity.getStatus(),2);
//            Optional<ContractDetailEntity>  contractDetailEntity = contractDetailRepository.findById(contractDetailId);
//            Assertions.assertTrue(contractDetailEntity.isPresent());
//            Assertions.assertNotNull(contractDetailEntity.get().getDriverEntity());
//            Assertions.assertEquals(contractDetailEntity.get().getDriverEntity().getId(),driverId);
//        }catch (Exception e){
//            Assertions.assertNull(e);
//        }
//    }
//
//    @DisplayName("Cancel Contract By Customer")
//    @Test
//    @Order(9)
//    public void cancelContractByCustomerTest() {
//        long contractId = 82;
//        CancelContractRequest cancelContractRequest = new CancelContractRequest();
//        cancelContractRequest.setContractId(82);
//        cancelContractRequest.setDoCustomer(true);
//        cancelContractRequest.setNote("Test Hủy hợp đồng");
//        contractService.cancelRenting(cancelContractRequest);
//        ContractEntity contractEntity = contractRepository.FindByID(contractId);
//        Assertions.assertNotNull(contractEntity);
//        Assertions.assertEquals(contractEntity.getStatus(),7);
//        Assertions.assertEquals(contractEntity.getNote(),cancelContractRequest.getNote());
//    }
//
//    @DisplayName("Cancel Contract By Staff")
//    @Test
//    @Order(10)
//    public void cancelContractByStaffTest() {
//        long contractId = 82;
//        CancelContractRequest cancelContractRequest = new CancelContractRequest();
//        cancelContractRequest.setContractId(82);
//        cancelContractRequest.setDoCustomer(false);
//        cancelContractRequest.setNote("Test Hủy hợp đồng Staff");
//        contractService.cancelRenting(cancelContractRequest);
//        ContractEntity contractEntity = contractRepository.FindByID(contractId);
//        Assertions.assertNotNull(contractEntity);
//        Assertions.assertEquals(contractEntity.getStatus(),7);
//        Assertions.assertEquals(contractEntity.getNote(),cancelContractRequest.getNote());
//    }
//
//    @DisplayName("Payment deposit By Customer")
//    @Test
//    @Order(11)
//    public void paymentDepositByCustomerTest() {
//        CustomerTransactionRequest customerTransactionRequest = new CustomerTransactionRequest();
//        long contractId = 82;
//        customerTransactionRequest.setContractId(contractId);
//        customerTransactionRequest.setPaid(50000);
//        customerTransactionRequest.setDeposit(true);
//        try {
//            // invalid case
//            contractService.addPaymentByCustomer(customerTransactionRequest);
//            ContractEntity contractEntity = contractRepository.FindByID(contractId);
//
//            List<PaymentEntity> entity = paymentsRepository.getListPaymentByContractId(contractId);
//            Assertions.assertEquals(0,entity.size());
//
//            customerTransactionRequest.setPaid(850000);
//            contractService.addPaymentByCustomer(customerTransactionRequest);
//            contractEntity = contractRepository.FindByID(contractId);
//            Assertions.assertEquals(4,contractEntity.getStatus());
//            List<PaymentEntity> entity1 = paymentsRepository.getListPaymentByContractId(contractId);
//            Assertions.assertEquals(1,entity1.size());
//            Assertions.assertEquals(850000,entity1.get(0).getPaid());
//            Assertions.assertEquals(1560000,entity1.get(0).getTotalAmount());
//            Assertions.assertEquals(1560000 - 850000,entity1.get(0).getReceivables());
//        }catch (Exception e){
//            Assertions.assertNull(e);
//        }
//    }
//
//    @DisplayName("Payment deposit By Customer")
//    @Test
//    @Order(12)
//    public void paymentDepositByStaffTest() {
//        PaymentRequest paymentRequest = new PaymentRequest();
//        long contractId = 82;
//        paymentRequest.setContractId(contractId);
//        paymentRequest.setPaid(50000);
//        paymentRequest.setDeposit(true);
//        paymentRequest.setAccountId(5);
//        try {
//            // invalid case
//            contractService.addPayment(paymentRequest);
//            ContractEntity contractEntity = contractRepository.FindByID(contractId);
//
//            List<PaymentEntity> entity = paymentsRepository.getListPaymentByContractId(contractId);
//            Assertions.assertEquals(0,entity.size());
//
//            paymentRequest.setPaid(850000);
//            contractService.addPayment(paymentRequest);
//            contractEntity = contractRepository.FindByID(contractId);
//            Assertions.assertEquals(4,contractEntity.getStatus());
//            List<PaymentEntity> entity1 = paymentsRepository.getListPaymentByContractId(contractId);
//            Assertions.assertEquals(1,entity1.size());
//            Assertions.assertEquals(850000,entity1.get(0).getPaid());
//            Assertions.assertEquals(1560000,entity1.get(0).getTotalAmount());
//            Assertions.assertEquals(1560000 - 850000,entity1.get(0).getReceivables());
//            Assertions.assertEquals(1,entity1.get(1).getStaffEntity().getId());
//        }catch (Exception e){
//            Assertions.assertNull(e);
//        }
//    }
//
//    @DisplayName("Payment By Customer")
//    @Test
//    @Order(13)
//    public void paymentNotDepositByTest() {
//        CustomerTransactionRequest customerTransactionRequest = new CustomerTransactionRequest();
//        long contractId = 82;
//        customerTransactionRequest.setContractId(contractId);
//        customerTransactionRequest.setPaid(50000);
//        customerTransactionRequest.setDeposit(false);
//        try {
//            // invalid case
//            contractService.addPaymentByCustomer(customerTransactionRequest);
//            ContractEntity contractEntity = contractRepository.FindByID(contractId);
//
//            List<PaymentEntity> entity = paymentsRepository.getListPaymentByContractId(contractId);
//            Assertions.assertEquals(0,entity.size());
//
//            customerTransactionRequest.setPaid(850000);
//            contractService.addPaymentByCustomer(customerTransactionRequest);
//            contractEntity = contractRepository.FindByID(contractId);
//            Assertions.assertEquals(4,contractEntity.getStatus());
//            List<PaymentEntity> entity1 = paymentsRepository.getListPaymentByContractId(contractId);
//            Assertions.assertEquals(1,entity1.size());
//            Assertions.assertEquals(850000,entity1.get(0).getPaid());
//            Assertions.assertEquals(1560000,entity1.get(0).getTotalAmount());
//            Assertions.assertEquals(1560000 - 850000,entity1.get(0).getReceivables());
//        }catch (Exception e){
//            Assertions.assertNull(e);
//        }
//    }
//
//    @DisplayName("Pay ment By Staff")
//    @Test
//    @Order(14)
//    public void paymentByTest() {
//        PaymentRequest paymentRequest = new PaymentRequest();
//        long contractId = 82;
//        paymentRequest.setContractId(contractId);
//        paymentRequest.setPaid(50000);
//        paymentRequest.setDeposit(false);
//        paymentRequest.setAccountId(5);
//        try {
//            // invalid case
//            contractService.addPayment(paymentRequest);
//            ContractEntity contractEntity = contractRepository.FindByID(contractId);
//
//            List<PaymentEntity> entity = paymentsRepository.getListPaymentByContractId(contractId);
//            Assertions.assertEquals(0,entity.size());
//
//            paymentRequest.setPaid(850000);
//            contractService.addPayment(paymentRequest);
//            contractEntity = contractRepository.FindByID(contractId);
//            Assertions.assertEquals(5,contractEntity.getStatus());
//            List<PaymentEntity> entity1 = paymentsRepository.getListPaymentByContractId(contractId);
//            Assertions.assertEquals(2,entity1.size());
//
//        }catch (Exception e){
//            Assertions.assertNull(e);
//        }
//    }
//
//    @DisplayName("get Car From parking")
//    @Test
//    @Order(15)
//    public void getCarFromParkingTest() {
//        long contractId = 82;
//        String plateNumber = "";
//        GetCarReQuest getCarReQuest = new GetCarReQuest();
//        getCarReQuest.setContractId(82);
//        List<String> plateNumbers = new ArrayList<>();
//        plateNumbers.add(plateNumber);
//        getCarReQuest.setPlateNumber(plateNumbers);
//        try {
//            contractService.comfirmGetCar(getCarReQuest);
//
//            ContractDetailEntity contractDetailEntity = contractDetailRepository.findContractDetailByContractIdByPlateNumber(contractId,plateNumber);
//
//            Assertions.assertNotNull(contractDetailEntity);
//            // check set pick up date in to database
//            Assertions.assertNotNull(contractDetailEntity.getReal_pick_up_date());
//            // check change car status
//            CarEntity carEntity = carRepository.findCarEntityByPlateNumber(plateNumber);
//            Assertions.assertNotNull(carEntity);
//            Assertions.assertEquals(2,carEntity.getStatus());
//            // check contract status
//            ContractEntity contractEntity = contractRepository.FindByID(contractId);
//            Assertions.assertEquals(5,contractEntity.getStatus());
//        }catch (Exception e){
//            Assertions.assertNull(e);
//        }
//    }
//
//    @DisplayName("Add Surcharge")
//    @Test
//    @Order(16)
//    public void addSurchargeTest() {
//        long staffAccountId = 8;
//        long driverAccountId = 6;
//        long contractId  = 82;
//        SurchargeRequest surchargeRequest = new SurchargeRequest();
//        surchargeRequest.setContractId(contractId);
//        surchargeRequest.setNote("Test Add Surchage");
//        surchargeRequest.setAmount(1200);
//        ResponseEntity<?> responseEntity = contractService.addSurcharge(surchargeRequest);
//        // error invalid StaffId
//        Assertions.assertEquals(500,responseEntity.getStatusCodeValue());
//        surchargeRequest.setStaffAccountId(staffAccountId);
//        ResponseEntity<?> responseEntity1 = contractService.addSurcharge(surchargeRequest);
//        // Valid Staff add Surchage
//        Assertions.assertEquals(200,responseEntity1.getStatusCodeValue());
//        surchargeRequest.setDriverAccountId(driverAccountId);
//        ResponseEntity<?> responseEntity2 = contractService.addSurcharge(surchargeRequest);
//        // Valid Staff add Surchage
//        Assertions.assertEquals(200,responseEntity2.getStatusCodeValue());
//
//
//
//
//    }
//
//    @DisplayName("Return Car ")
//    @Test
//    @Order(17)
//    public void returnCarTest() {
//
//    }
//
//    @DisplayName("Finish Contract")
//    @Test
//    @Order(18)
//    public void finishContractTest() {
//
//    }
//
//    @DisplayName("Get List Payment By Customer Id")
//    @Test
//    @Order(19)
//    public void getListPaymentByCustomerTest() {
//
//    }
//
//    @DisplayName("Get List Payment Cal By Contract")
//    @Test
//    @Order(20)
//    public void getListPaymentByContractTest() {
//
//    }
//
//    @DisplayName("Get List Surchage By Contract ")
//    @Test
//    @Order(21)
//    public void getListSurchageByContractTest() {
//
//    }
//
//    @DisplayName("Get List Payment By Contract ")
//    @Test
//    @Order(22)
//    public void getListPaymentTest() {
//
//    }
}
