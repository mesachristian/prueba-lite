package com.lite.backend.business;

import com.lite.backend.data.dto.CompanyDto;
import com.lite.backend.data.dto.CompanyProductsDto;
import com.lite.backend.data.dto.ProductDto;
import com.lite.backend.data.entity.Company;
import com.lite.backend.data.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<CompanyDto> getCompanies(){
        List<CompanyDto> result = companyRepository.findAll().stream().map(
                c -> new CompanyDto(c.getNit(), c.getName(), c.getAddress(), c.getPhone())).collect(Collectors.toList());

        return result;
    }

    public List<CompanyProductsDto> getCompaniesWithProducts(){
        List<CompanyProductsDto> result = companyRepository.findAll().stream().map(
                c -> new CompanyProductsDto(
                        c.getName(),
                        c.getProducts().stream().map( p -> new ProductDto(
                                p.getCode(),
                                p.getName(),
                                p.getCharacteristics(),
                                String.join(";", p.getPrices()),
                                p.getCompany().getName()
                        )).collect(Collectors.toList()))
        ).collect(Collectors.toList());

        return result;
    }

    public String createCompany(CompanyDto dto) throws Exception{

        Optional<Company> companyQuery = companyRepository.findByNit(dto.nit());

        if(companyQuery.isPresent()){
            throw new RuntimeException("Company already exists!");
        }

        Company newCompany = companyRepository.save(Company.builder()
                        .nit(dto.nit())
                        .name(dto.name())
                        .address(dto.address())
                        .phone(dto.phone())
                .build());

        return newCompany.getNit();
    }

    public void updateCompany(String nit, CompanyDto dto) throws Exception{
        Optional<Company> companyQuery = companyRepository.findByNit(nit);

        if(companyQuery.isEmpty()){
            throw new RuntimeException("Company not found");
        }

        Company c = companyQuery.get();

        c.setName(dto.name());
        c.setAddress(dto.address());
        c.setPhone(dto.phone());

        companyRepository.save(c);
    }
    public void deleteCompany(String nit) throws Exception{
        Optional<Company> companyQuery = companyRepository.findByNit(nit);

        if(companyQuery.isEmpty()){
            throw new RuntimeException("Company not found");
        }

        companyRepository.delete(companyQuery.get());
    }
}
