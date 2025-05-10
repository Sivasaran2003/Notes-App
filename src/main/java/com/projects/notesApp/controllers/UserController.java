package com.projects.notesApp.controllers;

import com.projects.notesApp.models.DTOs.UserVerifyDTO;
import com.projects.notesApp.models.User;
import com.projects.notesApp.services.UserService;
import jakarta.websocket.server.PathParam;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        if(updatedUser == null) {
            return new ResponseEntity<>("user no exists", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        boolean updated = userService.deleteUser(id);
        if(!updated) {
            return new ResponseEntity<>("user no exists", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("verified", HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody UserVerifyDTO userVerifyDTO) {
        boolean verified = userService.verifyUser(userVerifyDTO);
        if (!verified)
            return new ResponseEntity<>("Invalid username or password", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>("User verified", HttpStatus.OK);
    }
}
