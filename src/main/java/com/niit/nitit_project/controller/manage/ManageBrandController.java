package com.niit.nitit_project.controller.manage;

import com.niit.nitit_project.entity.Brand;
import com.niit.nitit_project.model.dto.BrandDTO;
import com.niit.nitit_project.service.manage.ManageBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ManageBrandController {
    @Autowired
    private ManageBrandService manageBrandService;

    @GetMapping("/admin/brand")
    public String loadPage(Model model,
                           @ModelAttribute("brandFind") BrandDTO brandDTO,
                           @RequestParam(value = "size", required = false, defaultValue = "6") Integer limit,
                           @RequestParam(value = "page", required = false, defaultValue = "0") Integer page){
        manageBrandService.loadPage(model, brandDTO, page, limit);
        return "admin/brand";
    }

    //API
    @GetMapping("/admin/brand/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        return ResponseEntity.ok(manageBrandService.getById(id));
    }
    @PostMapping("/admin/brand")
    public ResponseEntity<?> addBrand(@RequestBody BrandDTO brandDTO){
        return ResponseEntity.ok(manageBrandService.addBrand(brandDTO));
    }

    @PutMapping("/admin/brand")
    public ResponseEntity<?> updateBrand(@RequestBody BrandDTO brandDTO){
        return ResponseEntity.ok(manageBrandService.updateBrand(brandDTO));
    }

    @DeleteMapping("/admin/brand/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable Integer id){
        return ResponseEntity.ok(manageBrandService.deleteBrand(id));
    }
}
