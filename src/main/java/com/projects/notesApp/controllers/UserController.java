package com.projects.notesApp.controllers;

import com.projects.notesApp.exceptions.UserAlreadyExistsException;
import com.projects.notesApp.exceptions.UserDoesNotExistsException;
import com.projects.notesApp.models.UserDTOs.UserDTO;
import com.projects.notesApp.models.User;
import com.projects.notesApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody UserDTO user) {
        try {
            return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
        }catch (UserAlreadyExistsException u) {
            return new ResponseEntity<>(u.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO user) {
        return new ResponseEntity<>(userService.verify(user), HttpStatus.FOUND);
    }


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserDTO user) {
        try {
            User updatedUser = userService.updateUser(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }catch (UserDoesNotExistsException u) {
            return new ResponseEntity<>("user no exists", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        boolean updated = userService.deleteUser(id);
        if(!updated) {
            return new ResponseEntity<>("user no exists", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("verified", HttpStatus.OK);
    }
}
