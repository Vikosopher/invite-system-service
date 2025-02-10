package com.vik.invite_system_service.convertor;

import com.vik.invite_system_service.dto.UserDTO;
import com.vik.invite_system_service.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserConverter {

    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .active(user.isActive())
                .build();
    }

    public User toUser(UserDTO userDTO) {
        return User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .active(userDTO.isActive())
                .build();
    }
}
