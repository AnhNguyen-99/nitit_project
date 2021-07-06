package com.niit.nitit_project.service.shop;

import com.niit.nitit_project.model.dto.UserDTO;
import com.niit.nitit_project.model.response.ResponseNormal;

public interface RegistryService {
    ResponseNormal<UserDTO> createUser(UserDTO userDTO);
}
