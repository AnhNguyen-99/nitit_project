package com.niit.nitit_project.service.shop;

import com.niit.nitit_project.model.dto.CartWatchDTO;
import com.niit.nitit_project.model.response.ResponseNormal;
import org.springframework.ui.Model;

public interface CartService {
    ResponseNormal<CartWatchDTO> addCart(CartWatchDTO cartWatchDTO);
    Model loadCart(Model model);
    ResponseNormal<?> deleteCartWatch(Integer id);
    ResponseNormal<?> changeAmountCartWatch(CartWatchDTO cartWatchDTO);
}
