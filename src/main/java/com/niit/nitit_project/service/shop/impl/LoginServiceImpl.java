package com.niit.nitit_project.service.shop.impl;

import com.niit.nitit_project.model.dto.UserDTO;
import com.niit.nitit_project.model.mapper.UserMapper;
import com.niit.nitit_project.model.response.ResponseNormal;
import com.niit.nitit_project.repository.UserRepository;
import com.niit.nitit_project.sercurity.CustomUserDetails;
import com.niit.nitit_project.sercurity.JwtTokenUtil;
import com.niit.nitit_project.service.shop.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public ResponseNormal<String> doLogin(UserDTO userDTO, HttpServletRequest request,
                                           HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userDTO.getUsername(),
                    userDTO.getPassword()
            ));
            // Nếu không xảy ra exception tức là thông tin hợp lệ
            // Set thông tin authentication vào Security Context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Gen token
            String token = jwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());
            Cookie cookie = new Cookie("jwt_token", token);
            response.addCookie(cookie);

            ResponseNormal loginResponse = new ResponseNormal();
            loginResponse.setHttpStatus(HttpStatus.OK);
            loginResponse.setMsg("Đăng nhập thành công");
            loginResponse.setData(token);
            return loginResponse;
        } catch (Exception ex) {
            System.out.println("Tài khoản hoặc mật khẩu không chính xác!");
            return new ResponseNormal<>(HttpStatus.NOT_FOUND, "Tài khoản hoặc mật khẩu không chính xác!",null );
        }
    }
}
