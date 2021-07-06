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
import com.niit.nitit_project.service.shop.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private WatchRepository watchRepository;
    @Autowired
    private ImageRepository imageRepository;

    private final Integer LIMIT = 8;

    @Override
    public Model loadHomePage(Model model) {
        //Lấy danh sách brand
        List<BrandDTO> brandDTOList = brandRepository.findAll()
                .stream().map(o -> BrandMapper.toBrandDTO(o))
                .collect(Collectors.toList());
        model.addAttribute("listBrand", brandDTOList);
        //Lấy danh sách sản phẩm mới nhất
        List<WatchDTO> watchDTOS = watchRepository.getListNew(LIMIT, 1).stream()
                .map(o -> {
                    WatchDTO watchDTO = WatchMapper.toWatchDTO(o);
                    watchDTO.setImageDTOList(
                            imageRepository.getListByIdWatch(o.getId())
                                    .stream()
                                    .map(image -> ImageMapper.toImageDTO(image))
                                    .collect(Collectors.toList())
                    );
                    return watchDTO;
                }).collect(Collectors.toList());
        model.addAttribute("listWatchNew", watchDTOS);
        //Lấy danh sách sản phẩm đang giamr giá
        List<WatchDTO> saleWatchList = watchRepository.getListOnSale(LIMIT, 1).stream()
                .map(o -> {
                    WatchDTO watchDTO = WatchMapper.toWatchDTO(o);
                    watchDTO.setImageDTOList(
                            imageRepository.getListByIdWatch(o.getId())
                                    .stream()
                                    .map(image -> ImageMapper.toImageDTO(image))
                                    .collect(Collectors.toList())
                    );
                    return watchDTO;
                }).collect(Collectors.toList());
        model.addAttribute("listWatchSale", saleWatchList);
        return model;    }
}
