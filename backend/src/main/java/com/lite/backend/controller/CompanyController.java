package com.lite.backend.controller;

import com.lite.backend.business.CompanyService;
import com.lite.backend.data.dto.CompanyDto;
import com.lite.backend.data.dto.CompanyProductsDto;
import com.lite.backend.data.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/company")
@CrossOrigin
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanyDto>> getCompanies(){
        return ResponseEntity.ok(companyService.getCompanies());
    }

    @GetMapping
    @RequestMapping("/get-all")
    public ResponseEntity<List<CompanyProductsDto>> getCompaniesWithProducts(){
        return ResponseEntity.ok(companyService.getCompaniesWithProducts());
    }

    @PostMapping
    @RequestMapping("/create")
    public ResponseEntity<?> create(@RequestBody CompanyDto dto){
        try {
            return ResponseEntity.created(URI.create("/company/" + companyService.createCompany(dto))).build();
        }catch(Exception e){
            return ResponseEntity.badRequest().body( e.getMessage() );
        }
    }

    @PutMapping
    @RequestMapping("/update/{nit}")
    public ResponseEntity<?> update(@PathVariable("nit") String nit, @RequestBody CompanyDto dto){
        try{
            companyService.updateCompany(nit, dto);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping
    @RequestMapping("/{nit}")
    public ResponseEntity<?> delete(@PathVariable("nit") String nit){
        try{
            companyService.deleteCompany(nit);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
