package org.example.csaladi_app_kezelo_rendszer.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.csaladi_app_kezelo_rendszer.dto.ThemeDto;
import org.example.csaladi_app_kezelo_rendszer.entity.ThemeEntity;
import org.example.csaladi_app_kezelo_rendszer.mapper.ThemeMapper;
import org.example.csaladi_app_kezelo_rendszer.repository.ThemeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;
    private final ThemeMapper themeMapper;

    @Transactional
    public ThemeDto createTheme(ThemeDto theme) {
        ThemeEntity themeToCreate = ThemeEntity.builder().name(theme.getName()).build();
        ThemeEntity themeToReturn = themeRepository.save(themeToCreate);
        return themeMapper.toDto(themeToReturn);
    }

    @Transactional
    public ThemeDto updateTheme(String id, ThemeDto theme) {
        ThemeEntity themeToUpdate = themeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Theme not found!"));
        themeToUpdate.setName(theme.getName());
        ThemeEntity themeToReturn = themeRepository.save(themeToUpdate);
        return themeMapper.toDto(themeToReturn);
    }

    @Transactional
    public void deleteTheme(String id) {
        ThemeEntity themeToDelete = themeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Theme not found!"));
        themeRepository.delete(themeToDelete);
    }

    @Transactional(readOnly = true)
    public ThemeDto getThemeById(String id) {
        ThemeEntity themeToReturn = themeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Theme not found!"));
        return themeMapper.toDto(themeToReturn);
    }

    @Transactional(readOnly = true)
    public List<ThemeDto> getAllTheme() {
        List<ThemeEntity> listEntities = themeRepository.findAll();
        return listEntities.stream().map(themeMapper::toDto).toList();
    }
}
