package com.example.create_entity.Service.IService;

import com.example.create_entity.Entity.CarEntity;
import com.example.create_entity.Entity.CarImageEntity;
import com.example.create_entity.Repository.CarImageRepository;
import com.example.create_entity.dto.Request.CarImgRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
