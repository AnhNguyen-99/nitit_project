package com.niit.nitit_project.service.manage;

import com.niit.nitit_project.entity.Brand;
import com.niit.nitit_project.model.dto.BrandDTO;
import com.niit.nitit_project.model.response.ResponseNormal;
import org.springframework.ui.Model;

public interface ManageBrandService {
    Model loadPage(Model model, BrandDTO brandDTO, Integer page, Integer limit);
    ResponseNormal<?> addBrand(BrandDTO brandDTO);
    ResponseNormal<?> getById(Integer id);
    ResponseNormal<?> updateBrand(BrandDTO brandDTO);
    ResponseNormal<?> deleteBrand(Integer id);
}
