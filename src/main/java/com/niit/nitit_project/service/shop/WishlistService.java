package com.niit.nitit_project.service.shop;

import com.niit.nitit_project.model.response.ResponseNormal;
import org.springframework.ui.Model;

public interface WishlistService {
    Model loadPageWishlist(Model model, Integer idBrand, Integer page, Integer limit);
    ResponseNormal<?> addToWishlist(Integer idWatch);
}
