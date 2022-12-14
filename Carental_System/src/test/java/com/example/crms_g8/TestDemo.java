package com.example.crms_g8;


import com.example.crms_g8.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class TestDemo {

    @Autowired
    DriverRepository repo;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ContractDetailRepository contractDetailRepository;
    @Autowired
    CustomerRepository customerRepository;


    @Autowired
    DriverRepository driverRepository;


//    @Test
//    public void testDriver() {
//        Integer integer = customerRepository.CountNewCustomer();
//        System.out.println(integer);
//    }
//////}
//

//
//    @Test
//    public void testAccount() throws ParseException {
//
//        RoleEntity roleEntity = roleRepository.GetRoleDriver("Driver");
//
//
//        AccountEntity accountEntity = new AccountEntity();
//        accountEntity.setFullName("Nguyen Xuan Hieu");
//        accountEntity.setEmail("hieunxhe140911@fpt.edu.vn");
//        accountEntity.setUsername("hieu123");
//        accountEntity.setGender(1);
//        String sDate1="31/12/1998";
//        Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
//
//
//        accountEntity.setPhone("0368995147");
//        accountEntity.setDOB(date1);
//        accountEntity.setIdentity_Number("1999291211212121");
//        accountEntity.setIdentity_Picture_Back("img1.jpg");
//        accountEntity.setIdentity_Picture_Front("img2.jpg");
//        accountEntity.setAddress("Hung Yen1");
//        accountEntity.setPassword("PassWord1");
//        accountEntity.setRoleEntity(roleEntity);
//
//            AccountEntity account = accountRepository.save(accountEntity);
//        Assertions.assertThat(account.getID()).isGreaterThan(0);
//        Assertions.assertThat(account.getID()).isGreaterThan(0);
//
//
//        //   accountEntity.setPassword("PassWord1");
//
//
//    }

//    @Test
//    public void TestBDetail(){
//        BookingDetailService bookingDetailService ;
//
//      BookingDetailEntity  = bookingDetailService.ListBookingDetail(1L);
//        Assertions.assertThat(bookingDetailEntity).isNotNull();
//        System.out.println(bookingDetailEntity);
//
//    }


}
//}
