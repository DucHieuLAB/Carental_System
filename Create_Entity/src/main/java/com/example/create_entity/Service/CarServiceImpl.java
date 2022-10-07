package com.example.create_entity.Service;

import com.example.create_entity.Repository.CarRepository;
import com.example.create_entity.dto.Response.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    CarRepository carRepository;

    @Override
    public ResponseEntity<?> getListCapacity() {
        ResponseVo responseVo = new ResponseVo();
        List<Integer> result = carRepository.getListCapacity();
        Map<String, Object> map = new HashMap<>();
    if(result.isEmpty()){
        responseVo.setMessage("Danh sach trong");
        responseVo.setData(null);
        return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
    }
    map.put("capacity",result);
    responseVo.setMessage("");
    responseVo.setData(map);
    return new ResponseEntity<>(responseVo,HttpStatus.OK);
    }
}
