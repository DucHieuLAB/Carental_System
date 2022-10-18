package com.example.create_entity.Service;

import com.example.create_entity.Entity.CarEntity;
import com.example.create_entity.Entity.CarImageEntity;
import com.example.create_entity.Repository.CarImageRepository;
import com.example.create_entity.dto.Request.CarImgRequest;
import com.example.create_entity.dto.Response.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
@Service
public class CarImageServiceImpl implements CarImageService{
  @Autowired
    CarImageRepository carImageRepository;
    @Override
    public List<CarImageEntity> getListCarByPlateNumber(String plateNuber) {
        List<CarImageEntity> result = null;
        if(plateNuber == null || plateNuber.length() <=0 ){
            return result;
        }
        try {
            result =  carImageRepository.findCarImageEntitiesByPlateNumber(plateNuber);
        }catch (Exception e){
            throw e;
        }finally {
            return result;
        }
    }

    @Override
    public boolean addList(List<String> imgUrls, CarEntity carEntity) {
        boolean result = false;
        try {
            for (String imgUrl : imgUrls) {
                CarImageEntity carImageEntity = new CarImageEntity();
                carImageEntity.setPlateNumber(carEntity.getPlateNumber());
                carImageEntity.setImg(imgUrl);
                carImageEntity.setCar(carEntity);
                carImageEntity.setStatus(true);
                carImageRepository.save(carImageEntity);
            }
            result = true;
        }catch (Exception e){
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    @Override
    public boolean deleteList(Long carId) {
        boolean result = false;
        List<CarImageEntity> carImageEntities = carImageRepository.findCarImageEntitiesByCar_Id(carId);
        if(carImageEntities.isEmpty()){
            return true;
        }
        try {
            for(CarImageEntity carImageEntity : carImageEntities){
                carImageEntity.setStatus(false);
                carImageRepository.save(carImageEntity);
            }
        }catch (Exception e){
            e.printStackTrace();
            result = false;
        }finally {
            return result;
        }
    }
}