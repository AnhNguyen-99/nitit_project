package com.niit.nitit_project.service.manage;

import com.niit.nitit_project.model.dto.OrderDTO;
import com.niit.nitit_project.model.response.ResponseNormal;
import org.springframework.ui.Model;

public interface ManageOrderService {
    Model loadPage(Model model, Integer page, Integer limit);
    Model loadDetailProductPage(Model model, Integer idOrder);
    ResponseNormal<?> confirmOrder(Integer idOrder);
    ResponseNormal<?> changeStatusOrder(OrderDTO orderDTO);
}
