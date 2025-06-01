package com.projects.notesApp.models.DTOs;

import com.projects.notesApp.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public UserVerifyDTO userToUserDto(User user) {
        return new UserVerifyDTO(
                user.getUsername(),
                user.getPasswordHash()
        );
    }

    public User UserDTOUser(UserDTO newUserDTO) {
        return new User(
                newUserDTO.getUserName(), newUserDTO.getEmail(), newUserDTO.getPassword()
        );
    }
}
