package com.niit.nitit_project.service.shop.impl;

import com.niit.nitit_project.entity.Role;
import com.niit.nitit_project.entity.User;
import com.niit.nitit_project.model.dto.UserDTO;
import com.niit.nitit_project.model.mapper.UserMapper;
import com.niit.nitit_project.model.response.ResponseNormal;
import com.niit.nitit_project.repository.RoleRepository;
import com.niit.nitit_project.repository.UserRepository;
import com.niit.nitit_project.service.shop.RegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RegistryServiceImpl implements RegistryService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public ResponseNormal<UserDTO> createUser(UserDTO userDTO) {
        User userCheck = userRepository.findByUsername(userDTO.getUsername());
        if(userCheck != null){
            return new ResponseNormal<>(HttpStatus.BAD_REQUEST, "Username " + userDTO.getUsername() + " đã tồn tại!", null);
        }
        //Set ngày tạo
        userDTO.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        // Hash password using BCrypt
        String hash = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt(12));
        userDTO.setPassword(hash);
        //Set role USER
        Role role = roleRepository.findById(2).get();
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        userDTO.setRoles(roles);
        User user = userRepository.save(UserMapper.toUser(userDTO));
        return new ResponseNormal<UserDTO>(HttpStatus.OK, "Đăng ký thành công!", UserMapper.toUserDTO(user));
    }
}
