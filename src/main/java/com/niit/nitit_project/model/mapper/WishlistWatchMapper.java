package com.niit.nitit_project.model.mapper;

import com.niit.nitit_project.entity.WishlistWatch;
import com.niit.nitit_project.model.dto.WishlistWatchDTO;

public class WishlistWatchMapper {
    public static WishlistWatch toWishlistWatch(WishlistWatchDTO wishlistWatchDTO){
        WishlistWatch wishlistWatch = new WishlistWatch();
        wishlistWatch.setId(wishlistWatchDTO.getId());
        wishlistWatch.setIdWatch(wishlistWatchDTO.getIdWatch());
        wishlistWatch.setIdUser(wishlistWatchDTO.getIdUser());
        return wishlistWatch;
    }

    public static WishlistWatchDTO toWishlistWatchDTO(WishlistWatch wishlistWatch){
        WishlistWatchDTO wishlistWatchDTO = new WishlistWatchDTO();
        wishlistWatchDTO.setId(wishlistWatch.getId());
        wishlistWatchDTO.setIdWatch(wishlistWatch.getIdWatch());
        wishlistWatchDTO.setIdUser(wishlistWatch.getIdUser());
        return wishlistWatchDTO;
    }
}
