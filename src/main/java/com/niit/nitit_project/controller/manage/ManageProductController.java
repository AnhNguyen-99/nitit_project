package com.niit.nitit_project.controller.manage;

import com.niit.nitit_project.model.dto.WatchDTO;
import com.niit.nitit_project.service.manage.ManageProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ManageProductController {
    @Autowired
    private ManageProductService manageProductService;

    @GetMapping("/admin/product")
    public String loadPageManageProduct(Model model,
                                        @ModelAttribute("watchFind")WatchDTO watchDTO,
                                        @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                        @RequestParam(value = "size", required = false, defaultValue = "6") Integer limit){
        manageProductService.loadPage(model, watchDTO, page, limit);
        return "/admin/product";
    }

    //API
    @GetMapping("/admin/product/find/{id}")
    public ResponseEntity<?> getProductById(@PathVariable(value = "id") Integer id){
        return ResponseEntity.ok(manageProductService.findById(id));
    }

    @PostMapping("/admin/product/upload")
    public ResponseEntity<?> uploadImage(@RequestParam(value = "image") MultipartFile multipartFile){
        return ResponseEntity.ok(manageProductService.uploadFile(multipartFile));
    }

    @PostMapping("/admin/product")
    public ResponseEntity<?> addProduct(@RequestBody WatchDTO watchDTO){
        return ResponseEntity.ok(manageProductService.addProduct(watchDTO));
    }

    @PutMapping("/admin/product")
    public ResponseEntity<?> updateProduct(@RequestBody WatchDTO watchDTO){
        return ResponseEntity.ok(manageProductService.updateProduct(watchDTO));
    }

    @DeleteMapping("/admin/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") Integer id){
        return ResponseEntity.ok(manageProductService.deleteProduct(id));
    }
}
