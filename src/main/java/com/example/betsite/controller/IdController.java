package com.example.betsite.controller;

import com.example.betsite.model.User;
import com.example.betsite.service.CamundaService;
import com.example.betsite.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/id")
@RequiredArgsConstructor
public class IdController {
    private final CamundaService camundaService;
    private final UserService userService;

    @DeleteMapping()
    public void deleteInstance(@RequestHeader("key") String id) {
        camundaService.deleteProcessInstance(id);
    }

    @GetMapping
    public String registerId() {
        return camundaService.startProcess();
    }

    @GetMapping("/logout")
    public void logout(@RequestHeader("key") String key) {
        String state = camundaService.getCurrentStateOfProcess(key).getName();
        if (state.equals("Game List")) {
            camundaService.completeTask(key, "case", "logout");
        }
    }

    @PostMapping
    public ResponseEntity<String> goToCreateGame(@RequestHeader String key) {
        String userId = camundaService.getVariableFromProcess("userId", key);
        String state = camundaService.getCurrentStateOfProcess(key).getName();
        User user = userService.getUserById(userId);
        if (state.equals("Game List") && user.isAdmin()) {
            camundaService.completeTask(key, "case", "create");
            return ResponseEntity.status(HttpStatus.OK).body("Success");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid state");
    }
}
