package com.projects.notesApp.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private String userName;
    private String password;
    private String email;
}
