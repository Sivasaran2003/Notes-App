package com.projects.notesApp.models.NoteDTOs;

import com.projects.notesApp.models.Note;
import org.springframework.stereotype.Component;

@Component
public class NoteMapper {
    public NoteDTO noteToNoteDTO(Note note) {
        return new NoteDTO(
                note.getTitle(),
                note.getDescription()
        );
    }

    public Note NoteDTOtoNote(NoteDTO noteDTO) {
        return new Note(
                noteDTO.getTitle(),
                noteDTO.getDescription()
        );
    }
}
