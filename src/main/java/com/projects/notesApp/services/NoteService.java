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

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public List<NoteDTO> getNotes() {
        return noteRepository.findByUser_UserId(getCurrentUser().getUserId())
                .stream()
                .map(note -> noteMapper.noteToNoteDTO(note))
                .toList();
    }

    public Note addNote(NoteDTO noteDTO) {
        Note note = noteMapper.NoteDTOtoNote(noteDTO);
        note.setUser(getCurrentUser());
        note.setTitle(noteDTO.getTitle());
        note.setDescription(noteDTO.getDescription());
        return noteRepository.save(note);
    }
}
