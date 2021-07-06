package com.niit.nitit_project.model.mapper;

import com.niit.nitit_project.entity.User;
import com.niit.nitit_project.model.dto.UserDTO;

public class UserMapper {
    public static User toUser(UserDTO userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setFullName(userDTO.getFullName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setAge(userDTO.getAge());
        user.setAddress(userDTO.getAddress());
        user.setCreatedBy(userDTO.getCreatedBy());
        user.setCreatedDate(userDTO.getCreatedDate());
        user.setModifiedBy(userDTO.getModifiedBy());
        user.setModifiedDate(userDTO.getModifiedDate());
        user.setRoles(userDTO.getRoles());
        return user;
    }

    public static UserDTO toUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setFullName(user.getFullName());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setAge(user.getAge());
        userDTO.setAddress(user.getAddress());
        userDTO.setCreatedBy(user.getCreatedBy());
        userDTO.setCreatedDate(user.getCreatedDate());
        userDTO.setModifiedBy(user.getModifiedBy());
        userDTO.setModifiedDate(user.getModifiedDate());
        userDTO.setRoles(user.getRoles());
        return userDTO;
    }
}
