package org.example.csaladi_app_kezelo_rendszer.service;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.example.csaladi_app_kezelo_rendszer.dto.UserDto;
import org.example.csaladi_app_kezelo_rendszer.entity.BackgroundEntity;
import org.example.csaladi_app_kezelo_rendszer.entity.MenuEntity;
import org.example.csaladi_app_kezelo_rendszer.entity.ThemeEntity;
import org.example.csaladi_app_kezelo_rendszer.entity.UserEntity;
import org.example.csaladi_app_kezelo_rendszer.mapper.UserMapper;
import org.example.csaladi_app_kezelo_rendszer.repository.BackgroundRepository;
import org.example.csaladi_app_kezelo_rendszer.repository.MenuRepository;
import org.example.csaladi_app_kezelo_rendszer.repository.ThemeRepository;
import org.example.csaladi_app_kezelo_rendszer.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BackgroundRepository backgroundRepository;
    private final ThemeRepository themeRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserDto createUserProfile(UserDto userProfile) {
        ThemeEntity theme = null;
        BackgroundEntity background = null;
        MenuEntity menu = null;

        if (userProfile.getThemeId() != null) {
            theme = themeRepository.findById(userProfile.getThemeId()).orElseThrow(() -> new EntityNotFoundException("Theme not found!"));
        } else {
            theme = null;
        }

        if (userProfile.getBackgroundId() != null) {
            background = backgroundRepository.findById(userProfile.getBackgroundId()).orElseThrow(() -> new EntityNotFoundException("Background not found!"));
        } else {
            background = null;
        }

        if (userProfile.getMenuId() != null) {
            menu = menuRepository.findById(userProfile.getMenuId()).orElseThrow(() -> new EntityNotFoundException("Menu not found!"));
        } else {
            menu = MenuEntity.builder().name("Menu of " + userProfile.getName()).build();
        }

        UserEntity user = UserEntity.builder().name(userProfile.getName()).theme(theme).background(background).menu(menu).build();
        UserEntity returnEntity = userRepository.save(user);
        return userMapper.toDto(returnEntity);
    }

    @Transactional
    public UserDto updateUserProfile(String id, UserDto userProfile) {
        UserEntity userToModify = null;
        ThemeEntity theme = null;
        BackgroundEntity background = null;
        MenuEntity menu = null;

        userToModify = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found!"));

        if (userProfile.getThemeId() != null) {
            theme = themeRepository.findById(userProfile.getThemeId()).orElseThrow(() -> new EntityNotFoundException("Theme not found!"));
            userToModify.setTheme(theme);
        } else {
            theme = null;
        }

        if (userProfile.getBackgroundId() != null) {
            background = backgroundRepository.findById(userProfile.getBackgroundId()).orElseThrow(() -> new EntityNotFoundException("Background not found!"));
            userToModify.setBackground(background);
        } else {
            background = null;
        }

        if (userProfile.getMenuId() != null) {
            menu = menuRepository.findById(userProfile.getMenuId()).orElseThrow(() -> new EntityNotFoundException("Menu not found!"));
            userToModify.setMenu(menu);
        } else {
            menu = MenuEntity.builder().name("Menu of " + userProfile.getName()).build();
        }
        userToModify.setName(userProfile.getName());
        UserEntity returnEntity = userRepository.save(userToModify);
        return userMapper.toDto(returnEntity);
    }

    @Transactional
    public void deleteUserProfile(String id) {
        UserEntity userToDelete = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        userRepository.delete(userToDelete);
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(String id) {
        UserEntity userToReturn = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        return userMapper.toDto(userToReturn);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAllUser() {
        List<UserEntity> listEntities = userRepository.findAll();
        return listEntities.stream().map(userMapper::toDto).toList();
    }
}
