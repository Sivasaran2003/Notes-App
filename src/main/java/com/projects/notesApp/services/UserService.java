package com.projects.notesApp.services;

import com.projects.notesApp.exceptions.UserAlreadyExistsException;
import com.projects.notesApp.exceptions.UserDoesNotExistsException;
import com.projects.notesApp.models.AppUsers;
import com.projects.notesApp.models.DTOs.UserDTO;
import com.projects.notesApp.models.DTOs.UserDTO;
import com.projects.notesApp.models.DTOs.UserMapper;
import com.projects.notesApp.models.DTOs.UserVerifyDTO;
import com.projects.notesApp.models.User;
import com.projects.notesApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

// need to replace booleans, null with something
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper mapper;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public boolean verifyUser(UserVerifyDTO userVerifyDTO) throws UserDoesNotExistsException{
        User user = userRepository.findByMail(userVerifyDTO.getEmail());
        if(!userRepository.existsByMail(user.getEmail())) {
            throw new UserDoesNotExistsException(user.getEmail());
        }
        String storedHashPassword = user.getPasswordHash();
        String userInputPassword = userVerifyDTO.getPassword();
        return passwordEncoder.matches(userInputPassword, storedHashPassword);
    }

    public User addUser(UserDTO newUserDTO) throws UserAlreadyExistsException{
        User user = mapper.UserDTOUser(newUserDTO);
        if(userRepository.existsByMail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
        String hashedPassword = passwordEncoder.encode(newUserDTO.getPassword());
        user.setPasswordHash(hashedPassword);
        return userRepository.save(user);
    }

    public boolean deleteUser(int id) {
        if(!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    public User updateUser(UserDTO user) throws UserDoesNotExistsException{
        if(!userRepository.existsByMail(user.getEmail())) {
            throw new UserDoesNotExistsException(user.getEmail());
        }
        return userRepository.save(mapper.UserDTOUser(user));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new AppUsers(userRepository.findByMail(email));
    }
}
