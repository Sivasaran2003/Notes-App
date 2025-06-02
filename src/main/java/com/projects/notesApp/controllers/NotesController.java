package com.projects.notesApp.controllers;

import com.projects.notesApp.models.NoteDTOs.NoteDTO;
import com.projects.notesApp.models.NoteDTOs.NoteMapper;
import com.projects.notesApp.services.NoteService;
import com.projects.notesApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NotesController {
    @Autowired
    NoteService noteService;
    @Autowired
    NoteMapper noteMapper;

    @GetMapping
    public List<NoteDTO> getNotes() {
        return noteService.getNotes();
    }

    @PostMapping
    public NoteDTO addNote(@RequestBody NoteDTO note) {
        return noteMapper.noteToNoteDTO(noteService.addNote(note));
    }
}
