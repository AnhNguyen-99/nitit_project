package com.niit.nitit_project.controller.shop;

import com.niit.nitit_project.model.dto.CartWatchDTO;
import com.niit.nitit_project.model.dto.WatchDTO;
import com.niit.nitit_project.service.shop.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/cart")
    public String loadCart(Model model){
        cartService.loadCart(model);
        //Set object để tìm kiếm trên header
        model.addAttribute("watch", new WatchDTO());
        return "client/cart";
    }

    //API
    @PostMapping("client/cart")
    public ResponseEntity<?> addCart(@RequestBody CartWatchDTO cartWatchDTO){
        return ResponseEntity.ok(cartService.addCart(cartWatchDTO));
    }

    @PutMapping("client/cart")
    public ResponseEntity<?> changeAmountCartWatch(@RequestBody CartWatchDTO cartWatchDTO){
        return ResponseEntity.ok(cartService.changeAmountCartWatch(cartWatchDTO));
    }

    @DeleteMapping("client/cart/{id}")
    public ResponseEntity<?> deleteCartWatch(@PathVariable Integer id){
        return ResponseEntity.ok(cartService.deleteCartWatch(id));
    }
}
