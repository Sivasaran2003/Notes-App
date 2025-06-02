package com.projects.notesApp.repositories;

import com.projects.notesApp.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
    public List<Note> findByUser_UserId(Long userId);
}
