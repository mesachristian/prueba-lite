package com.lite.backend.business;

import com.lite.backend.data.dto.CompanyDto;
import com.lite.backend.data.dto.CreateProductDto;
import com.lite.backend.data.dto.ProductDto;
import com.lite.backend.data.entity.Company;
import com.lite.backend.data.entity.Product;
import com.lite.backend.data.repository.CompanyRepository;
import com.lite.backend.data.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDto> getAllProducts(){
        List<ProductDto> result = productRepository.findAll().stream().map(
                p -> new ProductDto(p.getCode(), p.getName(), p.getCharacteristics(), String.join(";", p.getPrices()), p.getCompany().getName())).collect(Collectors.toList());

        return result;
    }

    public String createProduct(CreateProductDto dto){
        Company company = companyRepository.findByNit(dto.companyNit()).orElseThrow(() -> new RuntimeException("Company not found"));

        Product newProduct = Product.builder()
                .name(dto.name())
                .characteristics(dto.characteristics())
                .prices(List.of(dto.prices().split(";")))
                .company(company)
                .build();

        company.getProducts().add(newProduct);
        //companyRepository.save(company);
        newProduct = productRepository.save(newProduct);

        return newProduct.getCode();
    }
}
