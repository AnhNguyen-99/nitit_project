package com.niit.nitit_project.service.shop;

import com.niit.nitit_project.model.dto.OrderDTO;
import org.springframework.ui.Model;

public interface OrderService {
    Model loadPageCheckout(Model model);
    void processCheckout(OrderDTO orderDTO);
    Model loadPageDisplayStatusOrder(Model model);
    Model loadPageOrderDetail(Integer id, Model model);
}
