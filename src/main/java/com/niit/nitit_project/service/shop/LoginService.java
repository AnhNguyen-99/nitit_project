package com.niit.nitit_project.service.shop;

import com.niit.nitit_project.model.dto.UserDTO;
import com.niit.nitit_project.model.response.ResponseNormal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginService {
    ResponseNormal<String> doLogin(UserDTO userDTO,
                                    HttpServletRequest request,
                                    HttpServletResponse response);
}
