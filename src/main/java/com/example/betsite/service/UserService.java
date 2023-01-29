package com.example.betsite.service;

import com.example.betsite.dto.UserDTO;
import com.example.betsite.model.User;
import com.example.betsite.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public boolean login(UserDTO userDTO) {
        User user = userRepository.getUserByUsername(userDTO.getUsername());
        return user.getPassword().equals(userDTO.getPassword());
    }

    public void createUser(UserDTO userDTO) {
        User user = new User(userDTO);
        userRepository.saveAndFlush(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public User getUserById(String id) {
        return userRepository.getUserById(id);
    }
}
