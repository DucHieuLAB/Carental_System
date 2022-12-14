package com.example.crms_g8.Service.IService;

import com.example.crms_g8.Entity.CarEntity;
import com.example.crms_g8.Entity.CarImageEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface CarImageService {
   List<CarImageEntity> getListCarByPlateNumber(String plateNuber);

   @Transactional
   boolean addList(List<String> imgUrls,CarEntity carEntity);

   @Transactional
   boolean deleteList(Long carId);

   @Transactional
   boolean updateList(List<String> imgUrls,CarEntity carEntity);
}
