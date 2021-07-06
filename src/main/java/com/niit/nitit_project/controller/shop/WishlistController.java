package com.niit.nitit_project.controller.shop;

import com.niit.nitit_project.service.shop.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WishlistController {
    @Autowired
    private WishlistService wishlistService;

    @GetMapping("/client/wishlist")
    public String loadPageWishlist(Model model,
                                   @RequestParam(value = "idBrand", required = false) Integer idBrand,
                                   @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                   @RequestParam(value = "limit", required = false, defaultValue = "6") Integer limit) {
        wishlistService.loadPageWishlist(model, idBrand, page, limit);
        return "client/wishlist";
    }

    //API
    @PostMapping("/client/wishlist/{id}")
    public ResponseEntity<?> addToWishlist(Model model, @PathVariable(value = "id") Integer idWatch) {
        return ResponseEntity.ok(wishlistService.addToWishlist(idWatch));
    }
}
