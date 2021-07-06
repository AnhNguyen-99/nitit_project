package com.niit.nitit_project.controller.shop;

import com.niit.nitit_project.model.dto.UserDTO;
import com.niit.nitit_project.service.shop.RegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistryController {
    @Autowired
    private RegistryService registryService;

    @GetMapping("/registry")
    public String registry(Model model){
        return "client/registry";
    }

    //API
    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(registryService.createUser(userDTO));
    }
}
