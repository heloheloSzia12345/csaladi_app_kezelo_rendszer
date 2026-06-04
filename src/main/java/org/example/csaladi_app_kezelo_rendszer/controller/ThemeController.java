package org.example.csaladi_app_kezelo_rendszer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.csaladi_app_kezelo_rendszer.dto.ThemeDto;
import org.example.csaladi_app_kezelo_rendszer.service.ThemeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/manager/theme")
public class ThemeController {

    private final ThemeService themeService;

    @GetMapping
    public ResponseEntity<List<ThemeDto>> getAllTheme() {
        return ResponseEntity.ok(themeService.getAllTheme());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThemeDto> getThemeById(@PathVariable String id) {
        return ResponseEntity.ok(themeService.getThemeById(id));
    }

    @PostMapping
    public ResponseEntity<ThemeDto> createTheme(@Valid @RequestBody ThemeDto theme) {
        return ResponseEntity.status(HttpStatus.CREATED).body(themeService.createTheme(theme));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ThemeDto> updateTheme(@PathVariable String id, @Valid @RequestBody ThemeDto theme) {
        return ResponseEntity.ok(themeService.updateTheme(id, theme));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable String id) {
        themeService.deleteTheme(id);
        return ResponseEntity.noContent().build();
    }
}
