package com.niit.nitit_project.service.shop.impl;

import com.niit.nitit_project.entity.User;
import com.niit.nitit_project.model.request.ChangePasswordRequest;
import com.niit.nitit_project.model.response.ResponseNormal;
import com.niit.nitit_project.repository.UserRepository;
import com.niit.nitit_project.sercurity.CustomUserDetails;
import com.niit.nitit_project.service.shop.ChangePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class ChangePasswordServiceImpl implements ChangePasswordService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Model loadPage(Model model) {
        model.addAttribute("changePassword", new ChangePasswordRequest());
        return model;
    }

    @Override
    public ResponseNormal<?> changePassword(ChangePasswordRequest cpr) {
        //Dùng Brypt để mã hóa mật khẩu thì không thể so sánh 2 giá trị giống nhau
        // Lưu trực tiếp mật khẩu được gửi lên và băm
        String hash = BCrypt.hashpw(cpr.getNewPassword(), BCrypt.gensalt(12));
        User user = ((CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser();
        user.setPassword(hash);
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    cpr.getNewPassword()
            ));
            return new ResponseNormal<>(HttpStatus.NOT_FOUND, "Mật khẩu mới trùng với mật khẩu hiện tại!", null);
        }catch (Exception e){
//            e.printStackTrace();
            userRepository.save(user);
            return new ResponseNormal<>(HttpStatus.OK, "Đổi mật khẩu thành công!", null);
        }
    }
}
