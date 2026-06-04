package org.example.csaladi_app_kezelo_rendszer.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.csaladi_app_kezelo_rendszer.dto.MenuDto;
import org.example.csaladi_app_kezelo_rendszer.entity.MenuEntity;
import org.example.csaladi_app_kezelo_rendszer.mapper.MenuMapper;
import org.example.csaladi_app_kezelo_rendszer.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    @Transactional
    public MenuDto createMenu(MenuDto menu) {
        MenuEntity menuToCreate = MenuEntity.builder().name(menu.getName()).build();
        MenuEntity menuToReturn = menuRepository.save(menuToCreate);
        return menuMapper.toDto(menuToReturn);
    }

    @Transactional
    public MenuDto updateMenu(String id, MenuDto menu) {
        MenuEntity menuToUpdate = menuRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Menu not found!"));
        menuToUpdate.setName(menu.getName());
        MenuEntity menuToReturn = menuRepository.save(menuToUpdate);
        return menuMapper.toDto(menuToReturn);
    }

    @Transactional
    public void deleteMenu(String id) {
        MenuEntity menuToDelete = menuRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Menu not found!"));
        menuRepository.delete(menuToDelete);
    }

    @Transactional(readOnly = true)
    public MenuDto getMenuById(String id) {
        MenuEntity menuToReturn = menuRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Menu not found!"));
        return menuMapper.toDto(menuToReturn);
    }

    @Transactional(readOnly = true)
    public List<MenuDto> getAllMenu() {
        List<MenuEntity> listEntities = menuRepository.findAll();
        return listEntities.stream().map(menuMapper::toDto).toList();
    }
}

