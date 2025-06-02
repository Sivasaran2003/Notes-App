package com.projects.notesApp.models.UserDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserVerifyDTO {
    private String email;
    private String password;
}
