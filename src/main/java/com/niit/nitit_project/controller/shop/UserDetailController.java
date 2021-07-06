package com.niit.nitit_project.controller.shop;

import com.niit.nitit_project.model.dto.UserDTO;
import com.niit.nitit_project.model.dto.WatchDTO;
import com.niit.nitit_project.service.shop.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserDetailController {
    @Autowired
    private UserDetailService userDetailService;

    @GetMapping(value = "/user/detail")
    public String loadPageUserDetail(Model model){
        userDetailService.loadPageUserDetail(model);
        //Set cho header tìm kiếm
        model.addAttribute("watch", new WatchDTO());
        return "client/user-detail";
    }

    //API
    @PutMapping (value = "/user/detail")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userDetailService.updateUser(userDTO));
    }
}
