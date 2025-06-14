package com.projects.notesApp.services;

import com.projects.notesApp.exceptions.UserDoesNotExistsException;
import com.projects.notesApp.models.Note;
import com.projects.notesApp.models.NoteDTOs.NoteDTO;
import com.projects.notesApp.models.NoteDTOs.NoteMapper;
import com.projects.notesApp.models.User;
import com.projects.notesApp.models.UserDTOs.UserMapper;
import com.projects.notesApp.repositories.NoteRepository;
import com.projects.notesApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    @Autowired
    NoteRepository noteRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    NoteMapper noteMapper;
    @Autowired
    UserService userService;

    public List<NoteDTO> getNotes() {
        return noteRepository.findByUser_UserId(userService.getCurrentUser().getUserId())
                .stream()
                .map(note -> noteMapper.noteToNoteDTO(note))
                .toList();
    }

    public Note addNote(NoteDTO noteDTO) {
        Note note = noteMapper.NoteDTOtoNote(noteDTO);
        note.setUser(userService.getCurrentUser());
        note.setTitle(noteDTO.getTitle());
        note.setDescription(noteDTO.getDescription());
        return noteRepository.save(note);
    }

    public void deleteNoteById(int id) {
        if(!userRepository.existsById(id)) throw new IllegalArgumentException("invalid note id");
        userRepository.deleteById(id);
    }

    public Note updateNote(Note note) {
        return noteRepository.save(note);
    }


}
