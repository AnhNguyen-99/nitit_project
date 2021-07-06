package com.niit.nitit_project.controller.shop;

import com.niit.nitit_project.model.dto.WatchDTO;
import com.niit.nitit_project.service.shop.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {
    @Autowired
    private HomeService homeService;

    @GetMapping({"/","/client/home"})
    public String loadHomePage( Model model){
        homeService.loadHomePage(model);
        model.addAttribute("watch", new WatchDTO());
        return "client/index";
    }
}
