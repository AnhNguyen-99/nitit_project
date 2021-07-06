package com.niit.nitit_project.model.mapper;

import com.niit.nitit_project.entity.Brand;
import com.niit.nitit_project.model.dto.BrandDTO;

public class BrandMapper {
    public static Brand toBrand(BrandDTO brandDTO){
        return new Brand(brandDTO.getId(), brandDTO.getName(), brandDTO.getLocation(), brandDTO.getIntroduce());
    }

    public static BrandDTO toBrandDTO(Brand brand){
        return new BrandDTO(brand.getId(), brand.getName(), brand.getLocation(), brand.getIntroduce());
    }
}
