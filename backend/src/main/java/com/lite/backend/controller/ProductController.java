package com.lite.backend.controller;

import com.lite.backend.business.ProductService;
import com.lite.backend.data.dto.CreateProductDto;
import com.lite.backend.data.dto.ProductDto;
import com.lite.backend.data.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @RequestMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateProductDto dto){
        try{
            return ResponseEntity.created(URI.create("/products" + productService.createProduct(dto))).build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }
}
