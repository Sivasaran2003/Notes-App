package com.projects.notesApp.controllers;

import com.projects.notesApp.models.Note;
import com.projects.notesApp.models.NoteDTOs.NoteDTO;
import com.projects.notesApp.models.NoteDTOs.NoteMapper;
import com.projects.notesApp.services.NoteService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NotesController {
    @Autowired
    NoteService noteService;
    @Autowired
    NoteMapper noteMapper;

    @GetMapping
    public ResponseEntity<List<NoteDTO>> getNotes() {
        return new ResponseEntity<>(noteService.getNotes(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<NoteDTO> addNote(@RequestBody NoteDTO note) {
        return new ResponseEntity<>(noteMapper.noteToNoteDTO(noteService.addNote(note)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable int id) {
        try {
            noteService.deleteNoteById(id);
            return new ResponseEntity<>("note deleted", HttpStatus.OK);
        }catch(IllegalArgumentException i) {
            return new ResponseEntity<>(i.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteDTO> updateNote(@PathVariable int id, @RequestBody NoteDTO noteDTO) {
        Note note =  noteMapper.NoteDTOtoNote(noteDTO);
        note.setNoteId(id);
        return new ResponseEntity<>(noteMapper.noteToNoteDTO(noteService.updateNote(note)), HttpStatus.OK);
    }
}
