package com.niit.nitit_project.service.shop.impl;

import com.niit.nitit_project.entity.User;
import com.niit.nitit_project.model.dto.UserDTO;
import com.niit.nitit_project.model.mapper.UserMapper;
import com.niit.nitit_project.model.response.ResponseNormal;
import com.niit.nitit_project.repository.UserRepository;
import com.niit.nitit_project.sercurity.CustomUserDetails;
import com.niit.nitit_project.service.shop.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class UserDetailServiceImpl implements UserDetailService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Model loadPageUserDetail(Model model) {
        try{
            //Lấy user hiện tại
            User user = ((CustomUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal())
                    .getUser();
            UserDTO userDTO = UserMapper.toUserDTO(user);
            model.addAttribute("user", userDTO);
            return model;
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("user", new UserDTO());
            return model;
        }
    }

    @Override
    public ResponseNormal<?> updateUser(UserDTO userDTO) {
        //Lấy user hiện tại
        User user = ((CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser();
        //Check trùng username
        User userCheck = userRepository.findByUsername(userDTO.getUsername());
        if(userCheck != null && !userCheck.getUsername().equals(user.getUsername())){
            return new ResponseNormal<>(HttpStatus.BAD_REQUEST, "Username " + userDTO.getUsername() + " đã tồn tại!", null);
        }
        user.setFullName(userDTO.getFullName());
        user.setUsername(userDTO.getUsername());
        user.setAddress(userDTO.getAddress());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setAge(userDTO.getAge());
        user.setModifiedDate(Timestamp.valueOf(LocalDateTime.now()));
        user.setModifiedBy(user.getId());
        userRepository.save(user);
        return new ResponseNormal<>(HttpStatus.OK, "Cập nhật hồ sơ thành công!", null);
    }
}
