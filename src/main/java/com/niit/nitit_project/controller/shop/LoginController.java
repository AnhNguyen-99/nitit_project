package com.niit.nitit_project.controller.shop;

import com.niit.nitit_project.model.dto.UserDTO;
import com.niit.nitit_project.service.shop.LoginService;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String login(){
        return "client/login";
    }

    @PostMapping("/do-login")
    public ResponseEntity<?> doLogin(@RequestBody UserDTO userDTO,
                                     HttpServletRequest request,
                                     HttpServletResponse response){
        return ResponseEntity.ok(loginService.doLogin(userDTO, request, response));
    }
}
