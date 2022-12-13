package com.example.create_entity.Service.ServiceImpl;

import com.example.create_entity.Entity.CarEntity;
import com.example.create_entity.Entity.CarImageEntity;
import com.example.create_entity.Repository.CarImageRepository;
import com.example.create_entity.Service.IService.CarImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
@Service
public class CarImageServiceImpl implements CarImageService {
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
                CarImageEntity checkImg = carImageRepository.getCarImageEntityByImgUrlAndCarPlateNumber(imgUrl,carEntity.getPlateNumber());
                if (ObjectUtils.isEmpty(checkImg)){
                    CarImageEntity carImageEntity = new CarImageEntity();
                    carImageEntity.setImg(imgUrl);
                    carImageEntity.setCar(carEntity);
                    carImageEntity.setStatus(true);
                    carImageRepository.save(carImageEntity);
                }
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

    @Override
    public boolean updateList(List<String> imgUrls, CarEntity carEntity) {
        List<CarImageEntity> carImageEntities = carImageRepository.findCarImageEntitiesByPlateNumber(carEntity.getPlateNumber());
        if (imgUrls.isEmpty()){
            return false;
        }
        if(!carImageEntities.isEmpty()){

            for (CarImageEntity img:carImageEntities
            ) {
                boolean esxit = false;
                for(String imgUrl: imgUrls){
                    if(img.getImg().equals(imgUrl)){
                        esxit = true;
                    }
                }
                if(esxit == false){
                    img.setStatus(false);
                    carImageRepository.save(img);
                }else {
                    for(String imgUrl: imgUrls){
                        if(img.getImg().equals(imgUrl)){
                            imgUrls.remove(imgUrl);
                            break;
                        }
                    }
                }
            }
        }
        for (String imgUrl : imgUrls) {
            CarImageEntity carImageEntity = new CarImageEntity();
            carImageEntity.setImg(imgUrl);
            carImageEntity.setCar(carEntity);
            carImageEntity.setStatus(true);
            carImageRepository.save(carImageEntity);
        }
        return true;
    }
}
