package com.example.create_entity.Service;

import com.example.create_entity.Entity.BrandEntity;
import com.example.create_entity.Repository.BrandRepository;
import com.example.create_entity.dto.Response.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import java.util.List;

public class BrandServiceImpl implements BrandService{
    @Autowired
    BrandRepository brandRepository;

    @Override
    public ResponseEntity<?> addBrand(BrandEntity brandEntity) {
        return null;
    }

    @Override
    public List<BrandEntity> getAll() {
        return null;
    }

    @Override
    public ResponseEntity<?> updateBrand(Long brandId, BrandEntity brandEntity) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteBrand(Long brandId) {
        return null;
    }

    @Override
    public ResponseEntity<?> findById(Long brandId, int pageIndex, int pageSize) {
        ResponseVo responseVo = new ResponseVo();
        if(ObjectUtils.isEmpty(brandId)){
            Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
            BrandEntity brandExist = brandRepository.findBrandEntityById(brandId);
            if(ObjectUtils.isEmpty(brandExist)){
                responseVo.setMessage("Brand Id khong ton tai");
            }else {
                responseVo.setData(brandExist);
            }
        }
        return new ResponseEntity<>("1", HttpStatus.OK);
    }
}
