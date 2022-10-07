package com.example.create_entity.Service;

import com.example.create_entity.Entity.BrandEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BrandService {

    public ResponseEntity<?> addBrand(BrandEntity brandEntity);

    public List<BrandEntity> getAll();

    @Transactional
    public ResponseEntity<?> updateBrand(Long brandId, BrandEntity brandEntity);

    @Transactional
    public ResponseEntity<?> deleteBrand(Long brandId);

    ResponseEntity<?> findById(Long brandId, int pageIndex, int pageSize);


}
