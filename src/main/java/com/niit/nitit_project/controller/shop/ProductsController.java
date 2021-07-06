package com.niit.nitit_project.controller.shop;

import com.niit.nitit_project.model.dto.WatchDTO;
import com.niit.nitit_project.service.shop.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductsController {
    @Autowired
    ProductsService productsService;

    @GetMapping("/client/products")
    public String loadPageProduct(@RequestParam(value = "idBrand",  required = false) Integer idBrand,
                                        @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                        @RequestParam(value = "limit", required = false, defaultValue = "6") Integer limit,
                                        Model model,
                                        @ModelAttribute("watch") WatchDTO watchDTO){
        if(idBrand != null){
            productsService.findByIdBrand(idBrand, model, page, limit);

        }else{
            productsService.findWatchByKeyWord(model, watchDTO, page, limit);
        }
        return "/client/list-product";
    }
}
