package com.niit.nitit_project.model.mapper;

import com.niit.nitit_project.entity.Cart;
import com.niit.nitit_project.model.dto.CartDTO;

public class CartMapper {
    public static Cart toCart (CartDTO cartDTO){
        Cart cart = new Cart();
        cart.setId(cartDTO.getId());
        cart.setIdUser(cartDTO.getIdUser());
        cart.setTotalPrice(cartDTO.getTotalPrice());
        cart.setUsername(cartDTO.getUsername());
        cart.setCreatedDate(cartDTO.getCreatedDate());
        return cart;
    }

    public static CartDTO toCartDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setIdUser(cart.getIdUser());
        cartDTO.setUsername(cart.getUsername());
        cartDTO.setTotalPrice(cart.getTotalPrice());
        cartDTO.setCreatedDate(cart.getCreatedDate());
        return cartDTO;
    }
}
