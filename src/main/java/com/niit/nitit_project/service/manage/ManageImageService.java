package com.niit.nitit_project.service.manage;

import com.niit.nitit_project.model.dto.ImageDTO;
import com.niit.nitit_project.model.response.ResponseNormal;
import org.springframework.ui.Model;

public interface ManageImageService {
    Model loadPage(Model model, Integer idWatch);
    ResponseNormal<?> addImage(ImageDTO imageDTO);
    ResponseNormal<?> deleteImage(Integer idImage);
}
