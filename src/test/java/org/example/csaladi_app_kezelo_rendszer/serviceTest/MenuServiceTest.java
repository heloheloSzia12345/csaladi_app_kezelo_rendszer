package org.example.csaladi_app_kezelo_rendszer.serviceTest;

import jakarta.persistence.EntityNotFoundException;
import org.example.csaladi_app_kezelo_rendszer.dto.MenuDto;
import org.example.csaladi_app_kezelo_rendszer.entity.MenuEntity;
import org.example.csaladi_app_kezelo_rendszer.mapper.MenuMapper;
import org.example.csaladi_app_kezelo_rendszer.repository.MenuRepository;
import org.example.csaladi_app_kezelo_rendszer.service.MenuService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @Mock
    MenuMapper menuMapper;

    @InjectMocks
    MenuService menuService;

    @Test
    public void Test1() {
        MenuDto menuDto = MenuDto.builder().name("name").build();
        MenuDto outputDto = MenuDto.builder().id("12345").name("name").build();
        MenuEntity menuEntity = MenuEntity.builder().id("12345").name("name").build();
        when(menuRepository.save(any(MenuEntity.class))).thenReturn(menuEntity);
        when(menuMapper.toDto(menuEntity)).thenReturn(outputDto);
        MenuDto result = menuService.createMenu(menuDto);
        assertNotNull(result);
        assertEquals("12345", result.getId());
        assertEquals("name", result.getName());
        verify(menuRepository, times(1)).save(any(MenuEntity.class));
        verify(menuMapper, times(1)).toDto(any(MenuEntity.class));
    }

    @Test
    public void Test2() {
        String id = "12345";
        MenuDto menuDto = MenuDto.builder().name("name").build();
        MenuEntity menuEntity = MenuEntity.builder().id("12345").name("change").build();
        MenuDto outputDto = MenuDto.builder().id("12345").name("name").build();
        MenuEntity outputEntity = MenuEntity.builder().id("12345").name("name").build();
        when(menuRepository.findById(id)).thenReturn(Optional.of(menuEntity));
        when(menuRepository.save(any(MenuEntity.class))).thenReturn(outputEntity);
        when(menuMapper.toDto(any(MenuEntity.class))).thenReturn(outputDto);
        MenuDto result = menuService.updateMenu(id, menuDto);
        assertNotNull(result);
        assertEquals("12345", result.getId());
        assertEquals("name", result.getName());
        verify(menuRepository, times(1)).findById(id);
        verify(menuRepository, times(1)).save(any(MenuEntity.class));
        verify(menuMapper, times(1)).toDto(any(MenuEntity.class));
    }

    @Test
    public void Test3() {
        String id = "12345";
        MenuDto menuDto = MenuDto.builder().name("name").build();
        when(menuRepository.findById(id)).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    menuService.updateMenu(id, menuDto);
                });
        assertEquals("Menu not found!", notFoundException.getMessage());

        verify(menuRepository, times(1)).findById(id);
        verify(menuRepository, times(0)).save(any(MenuEntity.class));
    }

    @Test
    public void Test4() {
        String id = "12345";
        MenuEntity menuEntity = MenuEntity.builder().id("12345").name("name").build();
        when(menuRepository.findById(id)).thenReturn(Optional.of(menuEntity));
        doNothing().when(menuRepository).delete(menuEntity);
        menuService.deleteMenu(id);
        verify(menuRepository, times(1)).findById(id);
        verify(menuRepository, times(1)).delete(menuEntity);
    }

    @Test
    public void Test5() {
        String id = "12345";
        when(menuRepository.findById(id)).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    menuService.deleteMenu(id);
                });
        assertEquals("Menu not found!", notFoundException.getMessage());
        verify(menuRepository, times(1)).findById(id);
        verify(menuRepository, times(0)).delete(any(MenuEntity.class));
    }

    @Test
    public void Test6() {
        String id = "12345";
        MenuEntity menuEntity = MenuEntity.builder().id("12345").name("name").build();
        MenuDto outputDto = MenuDto.builder().id("12345").name("name").build();
        when(menuRepository.findById(id)).thenReturn(Optional.of(menuEntity));
        when(menuMapper.toDto(any(MenuEntity.class))).thenReturn(outputDto);
        MenuDto result = menuService.getMenuById(id);
        assertNotNull(result);
        assertEquals("12345", result.getId());
        assertEquals("name", result.getName());

        verify(menuRepository, times(1)).findById(id);
        verify(menuMapper, times(1)).toDto(any(MenuEntity.class));
    }

    @Test
    public void Test7() {
        String id = "12345";
        when(menuRepository.findById(id)).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    menuService.getMenuById(id);
                });
        assertEquals("Menu not found!", notFoundException.getMessage());
        verify(menuRepository, times(1)).findById(id);
        verify(menuMapper, times(0)).toDto(any(MenuEntity.class));
    }

    @Test
    public void Test8() {
        MenuEntity menuEntity1 = MenuEntity.builder().id("1").name("10").build();
        MenuEntity menuEntity2 = MenuEntity.builder().id("2").name("20").build();
        MenuDto menuDto1 = MenuDto.builder().id("1").name("10").build();
        MenuDto menuDto2 = MenuDto.builder().id("2").name("20").build();
        when(menuRepository.findAll()).thenReturn(List.of(menuEntity1, menuEntity2));
        when(menuMapper.toDto(menuEntity1)).thenReturn(menuDto1);
        when(menuMapper.toDto(menuEntity2)).thenReturn(menuDto2);
        List<MenuDto> result = menuService.getAllMenu();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("2", result.get(1).getId());
        assertEquals("10", result.get(0).getName());
        assertEquals("20", result.get(1).getName());

        verify(menuRepository, times(1)).findAll();
        verify(menuMapper, times(2)).toDto(any(MenuEntity.class));
    }
}
