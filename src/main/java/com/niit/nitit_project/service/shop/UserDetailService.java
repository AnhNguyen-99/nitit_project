package com.niit.nitit_project.service.shop;

import com.niit.nitit_project.model.dto.UserDTO;
import com.niit.nitit_project.model.response.ResponseNormal;
import org.springframework.ui.Model;

public interface UserDetailService {
    Model loadPageUserDetail(Model model);
    ResponseNormal<?> updateUser(UserDTO userDTO);
}
