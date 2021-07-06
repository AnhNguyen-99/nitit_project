package com.niit.nitit_project.service.shop;

import org.springframework.ui.Model;

public interface SingleProductService {
    Model loadSingleProduct(Model model, Integer id);
}
