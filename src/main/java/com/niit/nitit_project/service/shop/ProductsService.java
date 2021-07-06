package com.niit.nitit_project.service.shop;

import com.niit.nitit_project.model.dto.WatchDTO;
import org.springframework.ui.Model;

public interface ProductsService {
    Model findByIdBrand(Integer id, Model model, Integer page, Integer limit);
    Model findWatchByKeyWord(Model model, WatchDTO watchDTO, Integer page, Integer limit);
}
