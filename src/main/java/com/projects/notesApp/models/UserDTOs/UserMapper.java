package com.projects.notesApp.models.UserDTOs;

import com.projects.notesApp.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserVerifyDTO userToUserVerifyDto(User user) {
        return new UserVerifyDTO(
                user.getUsername(),
                user.getPasswordHash()
        );
    }

    public UserDTO userToUserDTO(User user) {
        return new UserDTO(
                user.getEmail(),
                user.getPasswordHash(),
                user.getUsername()
        );
    }

    public User UserDTOUser(UserDTO newUserDTO) {
        return new User(
                newUserDTO.getUserName(), newUserDTO.getEmail(), newUserDTO.getPassword()
        );
    }
}
