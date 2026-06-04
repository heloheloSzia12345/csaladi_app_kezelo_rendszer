package org.example.csaladi_app_kezelo_rendszer.serviceTest;

import jakarta.persistence.EntityNotFoundException;
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
import org.example.csaladi_app_kezelo_rendszer.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private BackgroundRepository backgroundRepository;

    @Mock
    private ThemeRepository themeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    public void Test1() {
        UserDto userDto = UserDto.builder().name("name").themeId("theme").backgroundId("background").menuId("menu").build();
        ThemeEntity themeEntity = ThemeEntity.builder().id("theme").build();
        BackgroundEntity backgroundEntity = BackgroundEntity.builder().id("background").build();
        MenuEntity menuEntity = MenuEntity.builder().id("menu").build();
        UserEntity userEntity = UserEntity.builder().id("1").name("name").theme(themeEntity).background(backgroundEntity).menu(menuEntity).build();
        UserDto outputDto = UserDto.builder().id("1").name("name").themeId("theme").backgroundId("background").menuId("menu").build();
        when(themeRepository.findById("theme")).thenReturn(Optional.of(themeEntity));
        when(backgroundRepository.findById("background")).thenReturn(Optional.of(backgroundEntity));
        when(menuRepository.findById("menu")).thenReturn(Optional.of(menuEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.toDto(any(UserEntity.class))).thenReturn(outputDto);
        UserDto result = userService.createUserProfile(userDto);
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("name", result.getName());

        verify(themeRepository, times(1)).findById("theme");
        verify(backgroundRepository, times(1)).findById("background");
        verify(menuRepository, times(1)).findById("menu");
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void Test2() {
        UserDto userDto = UserDto.builder().name("name").themeId(null).backgroundId(null).menuId(null).build();
        MenuEntity defaultMenu = MenuEntity.builder().name("menu").build();
        UserEntity userEntity = UserEntity.builder().id("1").name("name").theme(null).background(null).menu(defaultMenu).build();
        UserDto outputDto = UserDto.builder().id("1").name("name").themeId(null).backgroundId(null).menuId(null).build();
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.toDto(any(UserEntity.class))).thenReturn(outputDto);
        UserDto result = userService.createUserProfile(userDto);
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("name", result.getName());

        verify(themeRepository, never()).findById(any());
        verify(backgroundRepository, never()).findById(any());
        verify(menuRepository, never()).findById(any());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void Test3() {
        UserDto userDto = UserDto.builder().name("name").themeId("theme").build();
        when(themeRepository.findById("theme")).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    userService.createUserProfile(userDto);
                });
        assertEquals("Theme not found!", notFoundException.getMessage());

        verify(themeRepository, times(1)).findById("theme");
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void Test4() {
        UserDto userDto = UserDto.builder().name("name").themeId("theme").backgroundId("background").build();
        ThemeEntity themeEntity = ThemeEntity.builder().id("theme").build();
        when(themeRepository.findById("theme")).thenReturn(Optional.of(themeEntity));
        when(backgroundRepository.findById("background")).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    userService.createUserProfile(userDto);
                });
        assertEquals("Background not found!", notFoundException.getMessage());

        verify(themeRepository, times(1)).findById("theme");
        verify(backgroundRepository, times(1)).findById("background");
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void Test5() {
        UserDto userDto = UserDto.builder().name("name").themeId("theme").backgroundId("background").menuId("menu").build();
        ThemeEntity themeEntity = ThemeEntity.builder().id("theme").build();
        BackgroundEntity backgroundEntity = BackgroundEntity.builder().id("background").build();
        when(themeRepository.findById("theme")).thenReturn(Optional.of(themeEntity));
        when(backgroundRepository.findById("background")).thenReturn(Optional.of(backgroundEntity));
        when(menuRepository.findById("menu")).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    userService.createUserProfile(userDto);
                });
        assertEquals("Menu not found!", notFoundException.getMessage());

        verify(themeRepository, times(1)).findById("theme");
        verify(backgroundRepository, times(1)).findById("background");
        verify(menuRepository, times(1)).findById("menu");
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void Test6() {
        String id = "1";
        UserDto userDto = UserDto.builder().name("name1").themeId("theme").backgroundId("background").menuId("menu").build();
        UserEntity existingUser = UserEntity.builder().id(id).name("name").build();
        ThemeEntity themeEntity = ThemeEntity.builder().id("theme").build();
        BackgroundEntity backgroundEntity = BackgroundEntity.builder().id("background").build();
        MenuEntity menuEntity = MenuEntity.builder().id("menu").build();
        UserEntity savedUser = UserEntity.builder().id(id).name("name1").theme(themeEntity).background(backgroundEntity).menu(menuEntity).build();
        UserDto outputDto = UserDto.builder().id(id).name("name1").themeId("theme").backgroundId("background").menuId("menu").build();
        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(themeRepository.findById("theme")).thenReturn(Optional.of(themeEntity));
        when(backgroundRepository.findById("background")).thenReturn(Optional.of(backgroundEntity));
        when(menuRepository.findById("menu")).thenReturn(Optional.of(menuEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUser);
        when(userMapper.toDto(any(UserEntity.class))).thenReturn(outputDto);
        UserDto result = userService.updateUserProfile(id, userDto);
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("name1", result.getName());

        verify(userRepository, times(1)).findById(id);
        verify(themeRepository, times(1)).findById("theme");
        verify(backgroundRepository, times(1)).findById("background");
        verify(menuRepository, times(1)).findById("menu");
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void Test7() {
        String id = "1";
        UserDto userDto = UserDto.builder().name("name1").themeId(null).backgroundId(null).menuId(null).build();
        UserEntity existingUser = UserEntity.builder().id(id).name("name").build();
        MenuEntity defaultMenu = MenuEntity.builder().name("menu").build();
        UserEntity savedUser = UserEntity.builder().id(id).name("name1").theme(null).background(null).menu(defaultMenu).build();
        UserDto outputDto = UserDto.builder().id(id).name("name1").themeId(null).backgroundId(null).menuId(null).build();
        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUser);
        when(userMapper.toDto(any(UserEntity.class))).thenReturn(outputDto);
        UserDto result = userService.updateUserProfile(id, userDto);
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("name1", result.getName());

        verify(userRepository, times(1)).findById(id);
        verify(themeRepository, never()).findById(any());
        verify(backgroundRepository, never()).findById(any());
        verify(menuRepository, never()).findById(any());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void Test8() {
        String id = "1";
        UserDto userDto = UserDto.builder().name("name").build();
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    userService.updateUserProfile(id, userDto);
                });
        assertEquals("User not found!", notFoundException.getMessage());

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void Test9() {
        String id = "1";
        UserDto userDto = UserDto.builder().name("name").themeId("theme").build();
        UserEntity existingUser = UserEntity.builder().id(id).name("name").build();
        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(themeRepository.findById("theme")).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    userService.updateUserProfile(id, userDto);
                });
        assertEquals("Theme not found!", notFoundException.getMessage());

        verify(userRepository, times(1)).findById(id);
        verify(themeRepository, times(1)).findById("theme");
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void Test10() {
        String id = "1";
        UserDto userDto = UserDto.builder().name("name").themeId("theme").backgroundId("background").build();
        UserEntity existingUser = UserEntity.builder().id(id).name("name").build();
        ThemeEntity themeEntity = ThemeEntity.builder().id("theme").build();
        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(themeRepository.findById("theme")).thenReturn(Optional.of(themeEntity));
        when(backgroundRepository.findById("background")).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    userService.updateUserProfile(id, userDto);
                });
        assertEquals("Background not found!", notFoundException.getMessage());

        verify(userRepository, times(1)).findById(id);
        verify(themeRepository, times(1)).findById("theme");
        verify(backgroundRepository, times(1)).findById("background");
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void Test11() {
        String id = "1";
        UserDto userDto = UserDto.builder().name("name").themeId("theme").backgroundId("background").menuId("menu").build();
        UserEntity existingUser = UserEntity.builder().id(id).name("name").build();
        ThemeEntity themeEntity = ThemeEntity.builder().id("theme").build();
        BackgroundEntity backgroundEntity = BackgroundEntity.builder().id("background").build();
        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(themeRepository.findById("theme")).thenReturn(Optional.of(themeEntity));
        when(backgroundRepository.findById("background")).thenReturn(Optional.of(backgroundEntity));
        when(menuRepository.findById("menu")).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    userService.updateUserProfile(id, userDto);
                });
        assertEquals("Menu not found!", notFoundException.getMessage());

        verify(userRepository, times(1)).findById(id);
        verify(themeRepository, times(1)).findById("theme");
        verify(backgroundRepository, times(1)).findById("background");
        verify(menuRepository, times(1)).findById("menu");
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void Test12() {
        String id = "12345";
        UserEntity userEntity = UserEntity.builder().id(id).name("name").build();
        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));
        doNothing().when(userRepository).delete(userEntity);
        userService.deleteUserProfile(id);

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).delete(userEntity);
    }

    @Test
    public void Test13() {
        String id = "12345";
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    userService.deleteUserProfile(id);
                });
        assertEquals("User not found!", notFoundException.getMessage());

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(0)).delete(any(UserEntity.class));
    }

    @Test
    public void Test14() {
        String id = "12345";
        UserEntity userEntity = UserEntity.builder().id(id).name("name").build();
        UserDto outputDto = UserDto.builder().id(id).name("name").build();
        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));
        when(userMapper.toDto(userEntity)).thenReturn(outputDto);
        UserDto result = userService.getUserById(id);
        assertNotNull(result);
        assertEquals("12345", result.getId());
        assertEquals("name", result.getName());

        verify(userRepository, times(1)).findById(id);
        verify(userMapper, times(1)).toDto(userEntity);
    }

    @Test
    public void Test15() {
        String id = "12345";
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    userService.getUserById(id);
                });
        assertEquals("User not found!", notFoundException.getMessage());

        verify(userRepository, times(1)).findById(id);
        verify(userMapper, times(0)).toDto(any(UserEntity.class));
    }

    @Test
    public void Test16() {
        UserEntity userEntity1 = UserEntity.builder().id("1").name("name1").build();
        UserEntity userEntity2 = UserEntity.builder().id("2").name("name2").build();
        UserDto userDto1 = UserDto.builder().id("1").name("name1").build();
        UserDto userDto2 = UserDto.builder().id("2").name("name2").build();
        when(userRepository.findAll()).thenReturn(List.of(userEntity1, userEntity2));
        when(userMapper.toDto(userEntity1)).thenReturn(userDto1);
        when(userMapper.toDto(userEntity2)).thenReturn(userDto2);
        List<UserDto> result = userService.getAllUser();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("2", result.get(1).getId());

        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(2)).toDto(any(UserEntity.class));
    }
}
