package org.example.csaladi_app_kezelo_rendszer.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.csaladi_app_kezelo_rendszer.dto.BackgroundDto;
import org.example.csaladi_app_kezelo_rendszer.entity.BackgroundEntity;
import org.example.csaladi_app_kezelo_rendszer.mapper.BackgroundMapper;
import org.example.csaladi_app_kezelo_rendszer.repository.BackgroundRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BackgroundService {

    private final BackgroundRepository backgroundRepository;
    private final BackgroundMapper backgroundMapper;

    @Transactional
    public BackgroundDto createBackground(BackgroundDto background) {
        BackgroundEntity backgroundToCreate = BackgroundEntity.builder().name(background.getName()).build();
        BackgroundEntity backgroundToReturn = backgroundRepository.save(backgroundToCreate);
        return backgroundMapper.toDto(backgroundToReturn);
    }

    @Transactional
    public BackgroundDto updateBackground(String id, BackgroundDto background) {
        BackgroundEntity backgroundToUpdate = backgroundRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Background not found!"));
        backgroundToUpdate.setName(background.getName());
        BackgroundEntity backgroundToReturn = backgroundRepository.save(backgroundToUpdate);
        return backgroundMapper.toDto(backgroundToReturn);
    }

    @Transactional
    public void deleteBackground(String id) {
        BackgroundEntity backgroundToDelete = backgroundRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Background not found!"));
        backgroundRepository.delete(backgroundToDelete);
    }

    @Transactional(readOnly = true)
    public BackgroundDto getBackgroundById(String id) {
        BackgroundEntity backgroundToReturn = backgroundRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Background not found!"));
        return backgroundMapper.toDto(backgroundToReturn);
    }

    @Transactional(readOnly = true)
    public List<BackgroundDto> getAllBackground() {
        List<BackgroundEntity> listEntities = backgroundRepository.findAll();
        return listEntities.stream().map(backgroundMapper::toDto).toList();
    }
}
