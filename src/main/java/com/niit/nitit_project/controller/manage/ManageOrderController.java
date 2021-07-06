package com.niit.nitit_project.controller.manage;

import com.niit.nitit_project.model.dto.OrderDTO;
import com.niit.nitit_project.service.manage.ManageOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ManageOrderController {
    @Autowired
    private ManageOrderService manageOrderService;

    @GetMapping("/admin/order")
    public String loadPage(Model model,
                           @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                           @RequestParam(value = "limit", required = false, defaultValue = "6") Integer limit){
        manageOrderService.loadPage(model, page, limit);
        return "admin/order";
    }

    @GetMapping("/admin/order/detail/{id}")
    public String loadDetailProductPage(Model model, @PathVariable Integer id){
        manageOrderService.loadDetailProductPage(model, id);
        return "admin/order-detail";
    }

    //API
    @GetMapping("/admin/order/confirm/{id}")
    public ResponseEntity<?> confirmOrder(@PathVariable(value = "id") Integer idOrder){
        return ResponseEntity.ok(manageOrderService.confirmOrder(idOrder));
    }
    @PutMapping("/admin/order/status")
    public ResponseEntity<?> changeStatusOrder(@RequestBody OrderDTO orderDTO){
        return ResponseEntity.ok(manageOrderService.changeStatusOrder(orderDTO));
    }
}
