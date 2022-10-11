package com.example.create_entity.Service;

import com.example.create_entity.Entity.BrandEntity;
import com.example.create_entity.Repository.BrandRepository;
import com.example.create_entity.dto.Request.BrandRequest;
import com.example.create_entity.dto.Response.BrandReponse;
import com.example.create_entity.dto.Response.ResponseVo;
import com.example.create_entity.untils.ResponseVeConvertUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    BrandRepository brandRepository;

    @Override
    public ResponseEntity<?> addBrand(BrandRequest brandEntity) {
        ResponseVo responseVo = new ResponseVo();
        if (ObjectUtils.isEmpty(brandEntity)) {
            responseVo.setStatus(false);
            responseVo.setMessage("invialid input, brand is empty");
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
        BrandEntity exsitBrand = brandRepository.findBrandEntityByName(brandEntity.getName());
        if (!ObjectUtils.isEmpty(exsitBrand)) {
            responseVo.setStatus(false);
            responseVo.setMessage("Hãng xe đã tồn tại");
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        try {
            BrandEntity newbrandEntity = new BrandEntity();
            newbrandEntity.setId(brandEntity.getId());
            newbrandEntity.setName(brandEntity.getName());
            newbrandEntity.setImg(brandEntity.getImg());
            newbrandEntity.setDescription(brandEntity.getDescription());
            newbrandEntity.setStatus(1);
            brandRepository.save(newbrandEntity);
            responseVo.setStatus(true);
            responseVo.setMessage("Tạo mới thành công");
            responseVo.setData(newbrandEntity);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        } catch (Exception e) {
            responseVo.setStatus(false);
            responseVo.setMessage("Lỗi khi tạo mới brand");
            responseVo.setData(e.getMessage());
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public List<BrandEntity> getAll() {
        try {
            List<BrandEntity> brandEntities = brandRepository.findAll();
            return brandEntities;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public ResponseEntity<?> findAll(int pageIndex, int pageSize, String brandName) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        String nameSearch = "";
        Page<BrandEntity> brandPage = null;
        if (ObjectUtils.isEmpty(brandName)) {
            brandPage = brandRepository.findBrandEntityByStatus(1, pageable);
        } else {
            brandPage = brandRepository.findBySearch("%" + brandName.trim() + "%", pageable);
        }

        ResponseVo responseVo = new ResponseVo();
        Map<String, Object> responseData = new HashMap<>();
        if (brandPage.isEmpty()) {
            responseData.put("brands", brandPage.getContent());
            responseData.put("Total Record", 0);
            responseVo.setMessage("Không tìm thấy thương hiệu liên quan tới từ khóa");
            responseVo.setData(responseData);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        responseData.put("brands", BrandReponse.createResponseData(brandPage.getContent()));
        responseData.put("brandName", brandName);
        responseData.put("currentPage", pageIndex);
        responseData.put("totalRecord", brandPage.getTotalElements());
        responseData.put("pageSize", brandPage.getSize());
        responseData.put("totalPage", brandPage.getTotalPages());
        responseVo.setData(responseData);
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateBrand(BrandRequest brandEntity) {
        ResponseVo responseVo = new ResponseVo();
        if (ObjectUtils.isEmpty(brandEntity)) {
            responseVo.setStatus(false);
            responseVo.setMessage("Invalid input");
        }
        BrandEntity exsitBrand = brandRepository.findBrandEntityById(brandEntity.getId());
        if (ObjectUtils.isEmpty(exsitBrand)) {
            responseVo.setStatus(false);
            responseVo.setMessage("Tên Thương Hiệu đã tồn tại");
        }
        try {
            BrandEntity newBrandEntity = new BrandEntity();
            newBrandEntity.setId(brandEntity.getId());
            newBrandEntity.setName(brandEntity.getName());
            newBrandEntity.setImg(brandEntity.getImg());
            newBrandEntity.setDescription(brandEntity.getDescription());
            newBrandEntity.setStatus(1);
            brandRepository.save(newBrandEntity);
            responseVo.setStatus(true);
            responseVo.setMessage("Cập nhật thành công !!");
            responseVo.setData(newBrandEntity);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        } catch (Exception e) {
            responseVo = ResponseVeConvertUntil.createResponseVo(true, "lỗi khi sửa thông tin Brand", e.getMessage());
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public ResponseEntity<?> deleteBrand(Long brandId) {
        ResponseVo responseVo = new ResponseVo();
        BrandEntity brandEntity = brandRepository.findBrandEntityById(brandId);
        if (ObjectUtils.isEmpty(brandEntity)) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Không tìm thấy Brand tương ứng", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        try {
            brandEntity.setStatus(0);
            brandRepository.save(brandEntity);
            responseVo = ResponseVeConvertUntil.createResponseVo(true, "Xóa Brand thành công", brandEntity);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        } catch (Exception e) {
            responseVo = ResponseVeConvertUntil.createResponseVo(true, "lỗi khi câp nhật thông tin Brand", e.getMessage());
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> findById(Long brandId, int pageIndex, int pageSize) {
        ResponseVo responseVo = new ResponseVo();
        if (ObjectUtils.isEmpty(brandId)) {
            Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
            BrandEntity brandExist = brandRepository.findBrandEntityById(brandId);
            if (ObjectUtils.isEmpty(brandExist)) {
                responseVo.setMessage("Brand Id khong ton tai");
            } else {
                responseVo.setData(brandExist);
            }
        }
        return new ResponseEntity<>("1", HttpStatus.OK);
    }


}
