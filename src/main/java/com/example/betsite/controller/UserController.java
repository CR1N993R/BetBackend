package com.example.betsite.controller;

import com.example.betsite.dto.UserDTO;
import com.example.betsite.model.User;
import com.example.betsite.service.CamundaService;
import com.example.betsite.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final CamundaService camundaService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> login(@RequestHeader String key, @RequestBody UserDTO userDTO) {
        String state = camundaService.getCurrentStateOfProcess(key).getName();
        if (state.equals("Select")) {
            boolean valid = userService.login(userDTO);
            User user = userService.getUserByUsername(userDTO.getUsername());
            if (valid) {
                camundaService.completeTask(key, "input", "login");
                //camundaService.completeTask(key);
                camundaService.addVariableToProcess("userId", user.getId(), key);
                return ResponseEntity.status(HttpStatus.OK).body("Success");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestHeader String key, @RequestBody UserDTO userDTO) {
        String state = camundaService.getCurrentStateOfProcess(key).getName();
        if (state.equals("Select")) {
            userService.createUser(userDTO);
            camundaService.completeTask(key, "input", "register");
            camundaService.completeTask(key);
            return ResponseEntity.status(HttpStatus.CREATED).body("Success");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid");
    }

    @GetMapping
    public ResponseEntity<User> getUser(@RequestHeader String key) {
        String userId = camundaService.getVariableFromProcess("userId", key);
        User user = userService.getUserById(userId);
        if (user != null) {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}
