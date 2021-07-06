package com.niit.nitit_project.service.shop.impl;

import com.niit.nitit_project.entity.Watch;
import com.niit.nitit_project.model.dto.BrandDTO;
import com.niit.nitit_project.model.dto.WatchDTO;
import com.niit.nitit_project.model.mapper.BrandMapper;
import com.niit.nitit_project.model.mapper.ImageMapper;
import com.niit.nitit_project.model.mapper.WatchMapper;
import com.niit.nitit_project.repository.BrandRepository;
import com.niit.nitit_project.repository.ImageRepository;
import com.niit.nitit_project.repository.WatchRepository;
import com.niit.nitit_project.service.shop.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImpl implements ProductsService {
    @Autowired
    private WatchRepository watchRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Model findByIdBrand(Integer id, Model model, Integer page, Integer limit) {
        //Lấy ds brand
        List<BrandDTO> brandDTOList = brandRepository.findAll()
                .stream()
                .map(o-> BrandMapper.toBrandDTO(o))
                .collect(Collectors.toList());
        model.addAttribute("listBrand", brandDTOList);
        //Lấy danh sách watch theo id brand
        Pageable pageable = PageRequest.of(page, limit);
        Page<WatchDTO> pageWatchDTO = watchRepository.findByIdBrand(id, pageable).map(o -> {
            WatchDTO watchDTO = WatchMapper.toWatchDTO(o);
            watchDTO.setImageDTOList(
                    imageRepository.getListByIdWatch(o.getId())
                            .stream().map(o1 -> ImageMapper.toImageDTO(o1))
                            .collect(Collectors.toList())
            );
            return watchDTO;
        });
        model.addAttribute("pageWatch", pageWatchDTO);
        return model;
    }

    @Override
    public Model findWatchByKeyWord(Model model, WatchDTO watchDTO, Integer page, Integer limit) {
        //Lấy ds brand
        List<BrandDTO> brandDTOList = brandRepository.findAll()
                .stream()
                .map(o-> BrandMapper.toBrandDTO(o))
                .collect(Collectors.toList());
        model.addAttribute("listBrand", brandDTOList);
        //Load page product
        if(watchDTO.getName() == null){
            watchDTO.setName("");
        }
        //Lấy ds watch theo keyword
        Pageable pageable = PageRequest.of(page, limit);
        Page<WatchDTO> pageWatchDTO = watchRepository
                .findByKeyWord(watchDTO.getName().trim(), pageable).map(o -> {
                    WatchDTO w = WatchMapper.toWatchDTO(o);
                    w.setImageDTOList(imageRepository.getListByIdWatch(w.getId())
                        .stream()
                        .map(image -> ImageMapper.toImageDTO(image))
                        .collect(Collectors.toList())
                    );
                    return w;
                });
        model.addAttribute("pageWatch", pageWatchDTO);
        return model;
    }
}
