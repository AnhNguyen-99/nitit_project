package com.niit.nitit_project.service.shop;

import com.niit.nitit_project.model.request.ChangePasswordRequest;
import com.niit.nitit_project.model.response.ResponseNormal;
import org.springframework.ui.Model;

public interface ChangePasswordService {
    Model loadPage(Model model);
    ResponseNormal<?> changePassword(ChangePasswordRequest changePasswordRequest);
}
