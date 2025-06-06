package com.projects.notesApp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Note{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noteId;

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
