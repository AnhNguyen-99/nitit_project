package com.niit.nitit_project.controller.manage;

import com.niit.nitit_project.model.dto.ImageDTO;
import com.niit.nitit_project.service.manage.ManageImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ManageImageController {
    @Autowired
    private ManageImageService manageImageService;

    @GetMapping("/admin/images/{id}")
    public String loadPage(Model model,
                           @PathVariable(value = "id") Integer idWatch){
        manageImageService.loadPage(model, idWatch);
        return "admin/product-image";
    }

    //API
    @PostMapping("/admin/image")
    public ResponseEntity<?> addImageProduct(@RequestBody ImageDTO imageDTO){
        return ResponseEntity.ok(manageImageService.addImage(imageDTO));
    }

    @DeleteMapping("/admin/image/{id}")
    public ResponseEntity<?> deleteImageProduct(@PathVariable(value = "id") Integer idImage){
        return ResponseEntity.ok(manageImageService.deleteImage(idImage));
    }
}
