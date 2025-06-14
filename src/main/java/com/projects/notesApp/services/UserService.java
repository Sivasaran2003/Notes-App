package com.projects.notesApp.services;

import com.projects.notesApp.exceptions.UserAlreadyExistsException;
import com.projects.notesApp.exceptions.UserDoesNotExistsException;
import com.projects.notesApp.models.UserAdapter;
import com.projects.notesApp.models.UserDTOs.UserDTO;
import com.projects.notesApp.models.UserDTOs.UserMapper;
import com.projects.notesApp.models.User;
import com.projects.notesApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    @Autowired
    private UserMapper mapper;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JWTService jwtService;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User addUser(UserDTO newUserDTO) throws UserAlreadyExistsException{
        User user = mapper.UserDTOUser(newUserDTO);
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
        user.setPasswordHash(passwordEncoder.encode(newUserDTO.getPassword()));
        return userRepository.save(user);
    }

    public String verify(UserDTO user) {
        System.out.println(user.getEmail() + " " + user.getPassword());
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if(authentication.isAuthenticated()) {
            return jwtService.generateToken(user);
        }

        return "not verified";
    }

    public boolean deleteUser(int id) {
        if(!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    public User updateUser(UserDTO user) throws UserDoesNotExistsException{
        if(!userRepository.existsByEmail(user.getEmail())) {
            throw new UserDoesNotExistsException(user.getEmail());
        }
        return userRepository.save(mapper.UserDTOUser(user));
    }
}
