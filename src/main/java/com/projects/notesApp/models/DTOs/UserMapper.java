package com.projects.notesApp.models.DTOs;

import com.projects.notesApp.models.User;

public class UserMapper {
    public UserVerifyDTO userToUserDto(User user) {
        return new UserVerifyDTO(
                user.getUserName(),
                user.getPassword()
        );
    }
}
