package org.example.csaladi_app_kezelo_rendszer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.csaladi_app_kezelo_rendszer.dto.BackgroundDto;
import org.example.csaladi_app_kezelo_rendszer.service.BackgroundService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager/background")
@RequiredArgsConstructor
@CrossOrigin("*")
public class BackgroundController {

    private final BackgroundService backgroundService;

    @GetMapping
    public ResponseEntity<List<BackgroundDto>> getAllBackground() {
        return ResponseEntity.ok(backgroundService.getAllBackground());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BackgroundDto> getBackgroundById(@PathVariable String id) {
        return ResponseEntity.ok(backgroundService.getBackgroundById(id));
    }

    @PostMapping
    public ResponseEntity<BackgroundDto> createBackground(@Valid @RequestBody BackgroundDto background) {
        return ResponseEntity.status(HttpStatus.CREATED).body(backgroundService.createBackground(background));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BackgroundDto> updateBackground(@PathVariable String id, @Valid @RequestBody BackgroundDto background) {
        return ResponseEntity.ok(backgroundService.updateBackground(id, background));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBackground(@PathVariable String id) {
        backgroundService.deleteBackground(id);
        return ResponseEntity.noContent().build();
    }
}
