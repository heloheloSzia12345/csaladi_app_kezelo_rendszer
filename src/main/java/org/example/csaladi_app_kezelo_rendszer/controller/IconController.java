package org.example.csaladi_app_kezelo_rendszer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.csaladi_app_kezelo_rendszer.dto.IconDto;
import org.example.csaladi_app_kezelo_rendszer.service.IconService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager/icon")
@CrossOrigin("*")
@RequiredArgsConstructor
public class IconController {

    private final IconService iconService;

    @GetMapping
    public ResponseEntity<List<IconDto>> getAllIcon() {
        return ResponseEntity.ok(iconService.getAllIcon());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IconDto> getIconById(@PathVariable String id) {
        return ResponseEntity.ok(iconService.getIconById(id));
    }

    @PostMapping
    public ResponseEntity<IconDto> createIcon(@Valid @RequestBody IconDto icon) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iconService.createIcon(icon));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IconDto> updateIcon(@PathVariable String id, @Valid @RequestBody IconDto icon) {
        return ResponseEntity.ok(iconService.updateIcon(id, icon));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIcon(@PathVariable String id) {
        iconService.deleteIcon(id);
        return ResponseEntity.noContent().build();
    }
}
