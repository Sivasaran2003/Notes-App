package com.projects.notesApp.models.NoteDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NoteDTO {
    private String title;
    private String description;
}
