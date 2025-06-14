package com.projects.notesApp.models.UserDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private String password;
    private String email;
}
