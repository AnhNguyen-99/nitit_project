package com.niit.nitit_project.model.mapper;

import com.niit.nitit_project.entity.CartWatch;
import com.niit.nitit_project.entity.User;
import com.niit.nitit_project.model.dto.CartWatchDTO;
import com.niit.nitit_project.model.dto.UserDTO;

public class CartWatchMapper {
    public static CartWatch toCartWatch(CartWatchDTO cartWatchDTO){
        CartWatch cartWatch = new CartWatch();
        cartWatch.setId(cartWatchDTO.getId());
        cartWatch.setIdCart(cartWatchDTO.getIdCart());
        cartWatch.setIdWatch(cartWatchDTO.getIdWatch());
        cartWatch.setNameWatch(cartWatchDTO.getNameWatch());
        cartWatch.setCount(cartWatchDTO.getCount());
        cartWatch.setPrice(cartWatchDTO.getPrice());
        return cartWatch;
    }

    public static CartWatchDTO toCartWatchDTO(CartWatch cartWatch){
        CartWatchDTO cartWatchDTO = new CartWatchDTO();
        cartWatchDTO.setId(cartWatch.getId());
        cartWatchDTO.setIdCart(cartWatch.getIdCart());
        cartWatchDTO.setIdWatch(cartWatch.getIdWatch());
        cartWatchDTO.setNameWatch(cartWatch.getNameWatch());
        cartWatchDTO.setCount(cartWatch.getCount());
        cartWatchDTO.setPrice(cartWatch.getPrice());
        return cartWatchDTO;
    }
}
