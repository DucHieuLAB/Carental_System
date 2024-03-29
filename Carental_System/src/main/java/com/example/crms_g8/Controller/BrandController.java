package com.example.crms_g8.Controller;

import com.example.crms_g8.Service.ServiceImpl.BrandServiceImpl;
import com.example.crms_g8.dto.Request.BrandRequest;
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
    public ResponseEntity<?> add(@RequestBody BrandRequest brandRequest) {
        return brandService.addBrand(brandRequest);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody BrandRequest brandRequest) {
        return brandService.updateBrand(brandRequest);
    }

    @DeleteMapping("/delete/{brandId}")
    public ResponseEntity<?> delete(@PathVariable Long brandId) {
        return brandService.deleteBrand(brandId);
    }

    @GetMapping
    public ResponseEntity<?> listBrand(@RequestParam(required = false) Integer pageIndex,
                                       @RequestParam(required = false) Integer pageSize,
                                       @RequestParam(required = false) String brandName) {
        if (pageIndex == null) {
            pageIndex = defaultPage;
        }
        if (pageSize == null) {
            pageSize = defaultSize;
        }
        return brandService.findAll(pageIndex, pageSize, brandName);
    }

    @GetMapping("/Detail/{id}")
    public ResponseEntity<?>DetailBrand(@PathVariable long id){
        return brandService.findOnedByID(id);
    }

}
