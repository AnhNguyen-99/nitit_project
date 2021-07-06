package com.niit.nitit_project.controller.shop;

import com.niit.nitit_project.model.dto.OrderDTO;
import com.niit.nitit_project.model.dto.WatchDTO;
import com.niit.nitit_project.service.shop.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/order/checkout")
    public String orderCheckout(Model model){
        orderService.loadPageCheckout(model);
        //Set object để tìm kiếm trên header
        model.addAttribute("watch", new WatchDTO());
        return "client/checkout";
    }

    @PostMapping("/order/checkout")
    public String processCheckout(Model model,
                                  @ModelAttribute("order") OrderDTO orderDTO){
        orderService.processCheckout(orderDTO);
        return "redirect:/order/history";
    }

    @GetMapping("/order/history")
    public String displayStatusOrder(Model model){
        orderService.loadPageDisplayStatusOrder(model);
        //Set object để tìm kiếm trên header
        model.addAttribute("watch", new WatchDTO());
        return "client/order-history";
    }

    @GetMapping("/order-detail/{id}")
    public String detailOrder(Model model,@PathVariable Integer id){
        orderService.loadPageOrderDetail(id, model);
        //Set object để tìm kiếm trên header
        model.addAttribute("watch", new WatchDTO());
        return "client/order-detail";
    }
}
