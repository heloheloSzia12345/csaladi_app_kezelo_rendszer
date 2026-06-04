package org.example.csaladi_app_kezelo_rendszer.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.csaladi_app_kezelo_rendszer.dto.IconDto;
import org.example.csaladi_app_kezelo_rendszer.entity.ApplicationEntity;
import org.example.csaladi_app_kezelo_rendszer.entity.IconEntity;
import org.example.csaladi_app_kezelo_rendszer.entity.MenuEntity;
import org.example.csaladi_app_kezelo_rendszer.mapper.IconMapper;
import org.example.csaladi_app_kezelo_rendszer.repository.ApplicationRepository;
import org.example.csaladi_app_kezelo_rendszer.repository.IconRepository;
import org.example.csaladi_app_kezelo_rendszer.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IconService {

    private final IconRepository iconRepository;
    private final ApplicationRepository applicationRepository;
    private final MenuRepository menuRepository;
    private final IconMapper iconMapper;

    @Transactional
    public IconDto createIcon(IconDto icon) {
        ApplicationEntity application = null;
        MenuEntity menu = null;
        IconEntity iconToCreate = null;

        if (icon.getApplicationId() != null) {
            application = applicationRepository.findById(icon.getApplicationId()).orElseThrow(() -> new EntityNotFoundException("Application not found!"));
        } else {
            application = null;
        }

        if (icon.getMenuId() != null) {
            menu = menuRepository.findById(icon.getMenuId()).orElseThrow(() -> new EntityNotFoundException("Menu not found!"));
        } else {
            menu = null;
        }

        iconToCreate = IconEntity.builder().name(icon.getName()).application(application).menu(menu).build();
        IconEntity iconToReturn = iconRepository.save(iconToCreate);
        return iconMapper.toDto(iconToReturn);
    }

    @Transactional
    public IconDto updateIcon(String id, IconDto icon) {
        IconEntity iconToUpdate = iconRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Icon not found!"));
        ApplicationEntity application = null;
        MenuEntity menu = null;

        if (icon.getApplicationId() != null) {
            application = applicationRepository.findById(icon.getApplicationId()).orElseThrow(() -> new EntityNotFoundException("Application not found!"));
        } else {
            application = null;
        }

        if (icon.getMenuId() != null) {
            menu = menuRepository.findById(icon.getMenuId()).orElseThrow(() -> new EntityNotFoundException("Menu not found!"));
        } else {
            menu = null;
        }

        iconToUpdate.setName(icon.getName());
        iconToUpdate.setApplication(application);
        iconToUpdate.setMenu(menu);
        IconEntity iconToReturn = iconRepository.save(iconToUpdate);
        return iconMapper.toDto(iconToReturn);
    }

    @Transactional
    public void deleteIcon(String id) {
        IconEntity iconToDelete = iconRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Icon not found!"));
        iconRepository.delete(iconToDelete);
    }

    @Transactional(readOnly = true)
    public IconDto getIconById(String id) {
        IconEntity iconToReturn = iconRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Icon not found!"));
        return iconMapper.toDto(iconToReturn);
    }

    @Transactional(readOnly = true)
    public List<IconDto> getAllIcon() {
        List<IconEntity> listEntities = iconRepository.findAll();
        return listEntities.stream().map(iconMapper::toDto).toList();
    }
}
