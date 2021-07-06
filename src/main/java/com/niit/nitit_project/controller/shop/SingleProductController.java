package com.niit.nitit_project.controller.shop;

import com.niit.nitit_project.service.shop.SingleProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SingleProductController {
    @Autowired
    private SingleProductService singleProductService;

    @GetMapping("/product/{id}")
    public String loadSingleProduct(@PathVariable(value = "id") Integer id, Model model){
        singleProductService.loadSingleProduct(model, id);
        return "client/single-product";
    }
}
