package com.projects.notesApp.services;

import com.projects.notesApp.exceptions.UserAlreadyExistsException;
import com.projects.notesApp.exceptions.UserDoesNotExistsException;
import com.projects.notesApp.models.UserAdapter;
import com.projects.notesApp.models.UserDTOs.UserDTO;
import com.projects.notesApp.models.UserDTOs.UserMapper;
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

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream().
                map(u -> mapper.userToUserDTO(u))
                .toList();
    }

    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User addUser(UserDTO newUserDTO) throws UserAlreadyExistsException{
        User user = mapper.UserDTOUser(newUserDTO);
        if(userRepository.existsByEmail(user.getEmail())) {
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
        if(!userRepository.existsByEmail(user.getEmail())) {
            throw new UserDoesNotExistsException(user.getEmail());
        }
        return userRepository.save(mapper.UserDTOUser(user));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Authenticating user: " + email);
        return new UserAdapter(userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found")));
    }
}
