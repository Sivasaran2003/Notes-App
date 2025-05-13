package com.projects.notesApp.services;

import com.projects.notesApp.models.DTOs.UserVerifyDTO;
import com.projects.notesApp.models.User;
import com.projects.notesApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

// need to replace booleans, null with something
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public boolean verifyUser(UserVerifyDTO userVerifyDTO) {
        List<User> users = userRepository.getUser(userVerifyDTO.getUserName());
        if(users.isEmpty()) return false;
        String storedHashPassword = users.get(0).getPassword();
        String userInputPassword = userVerifyDTO.getPassword();
        return passwordEncoder.matches(userInputPassword, storedHashPassword);
    }

    public User addUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    public boolean deleteUser(int id) {
        if(!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    public User updateUser(User user) {
        if(!userRepository.existsById(user.getUserId())) {
            return null;
        }
        return userRepository.save(user);
    }
}
