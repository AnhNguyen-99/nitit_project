package com.niit.nitit_project.controller.shop;

import com.niit.nitit_project.model.dto.WatchDTO;
import com.niit.nitit_project.model.request.ChangePasswordRequest;
import com.niit.nitit_project.service.shop.ChangePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.xml.ws.Response;

@Controller
public class ChangePasswordController {
    @Autowired
    private ChangePasswordService changePasswordService;

    @GetMapping(value = "/user/change-password")
    public String loadPageChangePassword(Model model){
        changePasswordService.loadPage(model);
        //Set object watch để trả về header
        model.addAttribute("watch", new WatchDTO());
        return "/client/change-password";
    }

    //API
    @PutMapping(value = "/user/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest){
        return ResponseEntity.ok(changePasswordService.changePassword(changePasswordRequest));
    }
}
