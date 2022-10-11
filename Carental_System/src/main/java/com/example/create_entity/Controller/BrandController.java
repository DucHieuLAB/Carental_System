package com.example.create_entity.Controller;

import com.example.create_entity.Service.BrandService;
import com.example.create_entity.Service.BrandServiceImpl;
import com.example.create_entity.dto.Request.BrandRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/brand")
public class BrandController {
private final int defaultPage = 1;
private final int defaultSize = 10;

    @Autowired
    BrandServiceImpl brandService;

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody BrandRequest brandRequest){
        return brandService.addBrand(brandRequest);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCategory(@RequestBody BrandRequest brandRequest){
        return brandService.updateBrand(brandRequest);
    }

    @DeleteMapping("/delete/{brandId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long brandId){
        return brandService.deleteBrand(brandId);
    }

    @GetMapping
    public ResponseEntity<?> listBrand(@RequestParam(required = false) Integer pageIndex,
                                       @RequestParam(required = false) Integer pageSize,
                                       @RequestParam(required = false) String brandName){
        if(pageIndex == null){
            pageIndex = defaultPage;
        }
        if(pageSize == null){
            pageSize = defaultSize;
        }
        return brandService.findAll(pageIndex,pageSize,brandName);
    }
}
