package org.example.csaladi_app_kezelo_rendszer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.csaladi_app_kezelo_rendszer.dto.UserDto;
import org.example.csaladi_app_kezelo_rendszer.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manager/user")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUserProfile(@Valid @RequestBody UserDto user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUserProfile(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUserProfile(@PathVariable String id, @Valid @RequestBody UserDto user) {
        return ResponseEntity.ok(userService.updateUserProfile(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserProfile(@PathVariable String id) {
        userService.deleteUserProfile(id);
        return ResponseEntity.noContent().build();
    }
}
