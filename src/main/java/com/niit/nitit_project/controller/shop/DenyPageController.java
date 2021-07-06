package com.niit.nitit_project.controller.shop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DenyPageController {
    @GetMapping("/403")
    public String accessDeniedPage(){
        return "client/403";
    }
}
