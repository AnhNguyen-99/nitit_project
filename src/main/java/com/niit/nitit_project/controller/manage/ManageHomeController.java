package com.niit.nitit_project.controller.manage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManageHomeController {
    @GetMapping("/admin/home")
    public String loadManageHome(){
        return "admin/home";
    }
}
