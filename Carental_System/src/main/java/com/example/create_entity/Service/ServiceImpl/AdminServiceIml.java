package com.example.create_entity.Service.ServiceImpl;

import com.example.create_entity.Entity.AdminEntity;
import com.example.create_entity.Entity.DistrictsEntity;
import com.example.create_entity.Repository.AdminRepository;
import com.example.create_entity.Repository.DistrictRepository;
import com.example.create_entity.Service.IService.AdminService;
import com.example.create_entity.dto.Request.UpdateInfoAdminRequest;
import com.example.create_entity.dto.Response.AdminInfoResponse;
import com.example.create_entity.dto.Response.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class AdminServiceIml implements AdminService {


    @Autowired
    AdminRepository adminRepository;
    @Autowired
    DistrictRepository districtRepository;

    @Override
    public ResponseEntity<?>DetailAdmin(String username) {
        AdminEntity adminEntity = adminRepository.GetByUsername(username);
        AdminInfoResponse adminInfoResponse = new AdminInfoResponse();
        adminInfoResponse = adminInfoResponse.adminInfoResponse(adminEntity);
        return new ResponseEntity<>(adminInfoResponse, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> Update(UpdateInfoAdminRequest InfoCustomerRequest) {
        ResponseVo responseVo = new ResponseVo();
        AdminEntity adminEntity = adminRepository.GetByUsername(InfoCustomerRequest.getUserName());
        String user_name = InfoCustomerRequest.getUserName();
        if (adminRepository.Check_Phone_Update(InfoCustomerRequest.getPhone(), user_name, adminEntity.getId()) != null) {
            responseVo.setMessage("Số điện thoại đã tồn tại trong hệ thống !");
            responseVo.setStatus(false);
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        } else if (adminRepository.Check_Identity_Update(InfoCustomerRequest.getIdentity_number(), user_name, adminEntity.getId()) != null) {
            responseVo.setMessage("Số chứng minh thư đã tồn tại trong hệ thống !");
            responseVo.setStatus(false);
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        } else {
            DistrictsEntity districtsEntity = new DistrictsEntity();
            adminEntity.setImg(InfoCustomerRequest.getImg_avt());
            adminEntity.setDOB(InfoCustomerRequest.getDob());
            adminEntity.setAddress(InfoCustomerRequest.getAddress());
            adminEntity.setIdentity_Picture_Front(InfoCustomerRequest.getIdentity_picture_front());
            adminEntity.setIdentity_Picture_Back(InfoCustomerRequest.getIdentity_picture_back());
            adminEntity.setIdentity_Number(InfoCustomerRequest.getIdentity_number());
            adminEntity.setPhone(InfoCustomerRequest.getPhone());
            adminEntity.setFullName(InfoCustomerRequest.getFullName());
            adminEntity.setGender(InfoCustomerRequest.getGender());
            adminEntity.setModifiedDate(new Date(System.currentTimeMillis()));

            List<DistrictsEntity> districtsEntities = districtRepository.check_district(
                    InfoCustomerRequest.getCity(),
                    InfoCustomerRequest.getWards(),
                    InfoCustomerRequest.getDistrict_Name());
            if (districtsEntities.isEmpty()) {
                districtsEntity.setCity(InfoCustomerRequest.getCity());
                districtsEntity.setDistrict_Name(InfoCustomerRequest.getDistrict_Name());
                districtsEntity.setWards(InfoCustomerRequest.getWards());
                adminEntity.setDistrictsEntity(districtsEntity);
                districtRepository.save(districtsEntity);
            } else {
                DistrictsEntity districts = districtRepository.check_districts(
                        InfoCustomerRequest.getCity(),
                        InfoCustomerRequest.getWards(),
                        InfoCustomerRequest.getDistrict_Name());
                adminEntity.setDistrictsEntity(districts);
            }
            adminRepository.save(adminEntity);
            responseVo.setMessage("Cập nhật tài khoản thành công!");
            responseVo.setStatus(true);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);

        }
    }
}
