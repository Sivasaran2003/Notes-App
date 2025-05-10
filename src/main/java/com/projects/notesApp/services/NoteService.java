package com.projects.notesApp.services;

import com.projects.notesApp.repositories.NoteRepository;
import com.projects.notesApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    @Autowired
    NoteRepository noteRepository;
    @Autowired
    UserRepository userRepository;
}
