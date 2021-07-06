package com.niit.nitit_project.service.manage;

import com.niit.nitit_project.model.dto.WatchDTO;
import com.niit.nitit_project.model.response.ResponseNormal;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

public interface ManageProductService {
    Model loadPage(Model model, WatchDTO watchDTO, Integer page, Integer limit);
    ResponseNormal<?> findById(Integer id);
    ResponseNormal<?> uploadFile(MultipartFile multipartFile);
    ResponseNormal<?> addProduct(WatchDTO watchDTO);
    ResponseNormal<?> deleteProduct(Integer id);
    ResponseNormal<?> updateProduct(WatchDTO watchDTO);
}
