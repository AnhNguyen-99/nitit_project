package com.niit.nitit_project.service.shop.impl;

import com.niit.nitit_project.model.dto.WatchDTO;
import com.niit.nitit_project.model.mapper.ImageMapper;
import com.niit.nitit_project.model.mapper.WatchMapper;
import com.niit.nitit_project.repository.ImageRepository;
import com.niit.nitit_project.repository.WatchRepository;
import com.niit.nitit_project.service.shop.SingleProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SingleProductServiceImpl implements SingleProductService {
    @Autowired
    private WatchRepository watchRepository;
    @Autowired
    private ImageRepository imageRepository;
    private final Integer LIMIT = 8;

    @Override
    public Model loadSingleProduct(Model model, Integer id) {
        //Lấy product theo id
        WatchDTO watchDTO = WatchMapper.toWatchDTO(watchRepository.findById(id).get());
        watchDTO.setImageDTOList(imageRepository
                                    .getListByIdWatch(id)
                                    .stream()
                                    .map(o-> ImageMapper.toImageDTO(o))
                                    .collect(Collectors.toList())
                                );
        model.addAttribute("watch", watchDTO);
        //Lấy danh sách sản phẩm mới
        List<WatchDTO> watchDTOS = watchRepository.getListNew(LIMIT, 1).stream()
                .map(o -> {
                    WatchDTO w = WatchMapper.toWatchDTO(o);
                    w.setImageDTOList(
                            imageRepository.getListByIdWatch(o.getId())
                                    .stream()
                                    .map(image -> ImageMapper.toImageDTO(image))
                                    .collect(Collectors.toList())
                    );
                    return w;
                }).collect(Collectors.toList());
        model.addAttribute("listWatchNew", watchDTOS);
        return model;
    }
}
