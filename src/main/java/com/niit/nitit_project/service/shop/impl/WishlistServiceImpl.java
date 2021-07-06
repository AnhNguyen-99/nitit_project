package com.niit.nitit_project.service.shop.impl;

import com.niit.nitit_project.entity.Brand;
import com.niit.nitit_project.entity.User;
import com.niit.nitit_project.entity.WishlistWatch;
import com.niit.nitit_project.model.dto.BrandDTO;
import com.niit.nitit_project.model.dto.WatchDTO;
import com.niit.nitit_project.model.mapper.BrandMapper;
import com.niit.nitit_project.model.mapper.ImageMapper;
import com.niit.nitit_project.model.mapper.WatchMapper;
import com.niit.nitit_project.model.response.ResponseNormal;
import com.niit.nitit_project.repository.BrandRepository;
import com.niit.nitit_project.repository.ImageRepository;
import com.niit.nitit_project.repository.WatchRepository;
import com.niit.nitit_project.repository.WishlistWatchRepository;
import com.niit.nitit_project.sercurity.CustomUserDetails;
import com.niit.nitit_project.service.shop.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private WatchRepository watchRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private WishlistWatchRepository wishlistWatchRepository;

    @Override
    public Model loadPageWishlist(Model model, Integer idBrand, Integer page, Integer limit) {
        //Lấy ds brand
        List<BrandDTO> brandDTOList = brandRepository.findAll()
                                                    .stream()
                                                    .map(o-> BrandMapper.toBrandDTO(o))
                                                    .collect(Collectors.toList());
        model.addAttribute("listBrand", brandDTOList);
        //Lấy ds sản phẩm yêu thích theo user
        User user = ((CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser();
        Pageable pageable = PageRequest.of(page, limit);
        if(idBrand != null){
            Page<WatchDTO> watchDTOList = watchRepository.findWishlistOfUserByIdBrand(user.getId(), idBrand, pageable)
                    .map(o-> {
                        WatchDTO w = WatchMapper.toWatchDTO(o);
                        w.setImageDTOList(imageRepository.getListByIdWatch(w.getId())
                                .stream()
                                .map(o1-> ImageMapper.toImageDTO(o1))
                                .collect(Collectors.toList()));
                        return w;
                    });
            model.addAttribute("pageWatch", watchDTOList);
        }else{
            Page<WatchDTO> watchDTOList = watchRepository.findWishlistByIdUser(user.getId(), pageable)
                    .map(o-> {
                        WatchDTO w = WatchMapper.toWatchDTO(o);
                        w.setImageDTOList(imageRepository.getListByIdWatch(w.getId())
                                .stream()
                                .map(o1-> ImageMapper.toImageDTO(o1))
                                .collect(Collectors.toList()));
                        return w;
                    });
            model.addAttribute("pageWatch", watchDTOList);
        }
        //Set watch cho header tìm kiếm
        model.addAttribute("watch", new WatchDTO());
        return model;
    }

    @Override
    public ResponseNormal<?> addToWishlist(Integer idWatch) {
        try{
            User user = ((CustomUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal())
                    .getUser();
            if(wishlistWatchRepository.findByIdUserAndIdWatch(user.getId(), idWatch).isPresent()){
                return new ResponseNormal<>(HttpStatus.BAD_REQUEST, "Sản phẩm đã trong danh sách yêu thích của bạn!", null);
            }
            WishlistWatch wishlistWatch = new WishlistWatch();
            wishlistWatch.setIdWatch(idWatch);
            wishlistWatch.setIdUser(user.getId());
            wishlistWatchRepository.save(wishlistWatch);
            return new ResponseNormal<>(HttpStatus.OK, "Đã thêm vào danh sách yêu thích", null);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseNormal<>(HttpStatus.FORBIDDEN, "Bạn chưa đăng nhập", null);
        }
    }
}
