package com.projects.notesApp.controllers;

import com.projects.notesApp.services.NoteService;
import com.projects.notesApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notes")
public class NotesController {
    @Autowired
    UserService userService;
    @Autowired
    NoteService noteService;
}
