package com.niit.nitit_project.service.manage.Impl;

import com.niit.nitit_project.entity.Brand;
import com.niit.nitit_project.entity.Watch;
import com.niit.nitit_project.model.dto.BrandDTO;
import com.niit.nitit_project.model.mapper.BrandMapper;
import com.niit.nitit_project.model.response.ResponseNormal;
import com.niit.nitit_project.repository.BrandRepository;
import com.niit.nitit_project.repository.WatchRepository;
import com.niit.nitit_project.service.manage.ManageBrandService;
import com.niit.nitit_project.service.manage.ManageImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ManageBrandServiceImpl implements ManageBrandService {
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private WatchRepository watchRepository;

    @Override
    public Model loadPage(Model model, BrandDTO brandDTO, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("id").descending());
        Page<BrandDTO> brandDTOPage;
        if(brandDTO.getName() == null || brandDTO.getName() == ""){
            brandDTOPage = brandRepository.findAll(pageable).map(o-> BrandMapper.toBrandDTO(o));
        }else{
            brandDTOPage = brandRepository.findByKeyWord(brandDTO.getName(), pageable).map(o -> BrandMapper.toBrandDTO(o));
        }
        model.addAttribute("listBrand", brandDTOPage);
        return model;
    }

    @Override
    public ResponseNormal<?> addBrand(BrandDTO brandDTO) {
        if(checkExitsName(brandDTO.getName())){
            return new ResponseNormal<>(HttpStatus.BAD_REQUEST, "Tên thương hiệu đã tồn tại!", null);
        }
        brandRepository.save(BrandMapper.toBrand(brandDTO));
        return new ResponseNormal<>(HttpStatus.OK, "Thêm mới thành công!", null);
    }

    @Override
    public ResponseNormal<?> getById(Integer id) {
        return new ResponseNormal<>(
                HttpStatus.OK,
                "Success",
                BrandMapper.toBrandDTO(brandRepository.findById(id).get()));
    }

    @Override
    public ResponseNormal<?> updateBrand(BrandDTO brandDTO) {
        Optional<Brand> brand = brandRepository.findByName(brandDTO.getName());
        if(brand.isPresent() && !brand.get().getId().equals(brandDTO.getId())){
            return new ResponseNormal<>(HttpStatus.BAD_REQUEST, "Tên thương hiệu đã tồn tại!", null);
        }
        brandRepository.save(BrandMapper.toBrand(brandDTO));
        return new ResponseNormal<>(HttpStatus.OK, "Cập nhật thương hiệu thành công!", null);
    }

    @Override
    public ResponseNormal<?> deleteBrand(Integer id) {
        //Check có sản phẩm thuộc brand cần xóa
        Pageable pageable = PageRequest.of(0, 10);
        Page<Watch> watchList = watchRepository.findByIdBrand(id, pageable);
        if(watchList.getContent().size() > 0){
            return new ResponseNormal<>(HttpStatus.BAD_REQUEST, "Vẫn còn sản phẩm thuộc thương hiệu này, không thể xóa", null);
        }
        brandRepository.deleteById(id);
        return new ResponseNormal<>(HttpStatus.OK, "Xóa thành công!", null);
    }

    public Boolean checkExitsName(String name){
        Optional<Brand> brand = brandRepository.findByName(name);
        if(brand.isPresent()){
            return true;
        }
        return false;
    }
}
