package org.example.csaladi_app_kezelo_rendszer.serviceTest;

import jakarta.persistence.EntityNotFoundException;
import org.example.csaladi_app_kezelo_rendszer.dto.ThemeDto;
import org.example.csaladi_app_kezelo_rendszer.entity.ThemeEntity;
import org.example.csaladi_app_kezelo_rendszer.mapper.ThemeMapper;
import org.example.csaladi_app_kezelo_rendszer.repository.ThemeRepository;
import org.example.csaladi_app_kezelo_rendszer.service.ThemeService;
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
public class ThemeServiceTest {

    @Mock
    private ThemeRepository themeRepository;

    @Mock
    ThemeMapper themeMapper;

    @InjectMocks
    ThemeService themeService;

    @Test
    public void Test1() {
        ThemeDto themeDto = ThemeDto.builder().name("name").build();
        ThemeDto outputDto = ThemeDto.builder().id("12345").name("name").build();
        ThemeEntity themeEntity = ThemeEntity.builder().id("12345").name("name").build();
        when(themeRepository.save(any(ThemeEntity.class))).thenReturn(themeEntity);
        when(themeMapper.toDto(themeEntity)).thenReturn(outputDto);
        ThemeDto result = themeService.createTheme(themeDto);
        assertNotNull(result);
        assertEquals("12345", result.getId());
        assertEquals("name", result.getName());
        verify(themeRepository, times(1)).save(any(ThemeEntity.class));
        verify(themeMapper, times(1)).toDto(any(ThemeEntity.class));
    }

    @Test
    public void Test2() {
        String id = "12345";
        ThemeDto themeDto = ThemeDto.builder().name("name").build();
        ThemeEntity themeEntity = ThemeEntity.builder().id("12345").name("change").build();
        ThemeDto outputDto = ThemeDto.builder().id("12345").name("name").build();
        ThemeEntity outputEntity = ThemeEntity.builder().id("12345").name("name").build();
        when(themeRepository.findById(id)).thenReturn(Optional.of(themeEntity));
        when(themeRepository.save(any(ThemeEntity.class))).thenReturn(outputEntity);
        when(themeMapper.toDto(any(ThemeEntity.class))).thenReturn(outputDto);
        ThemeDto result = themeService.updateTheme(id, themeDto);
        assertNotNull(result);
        assertEquals("12345", result.getId());
        assertEquals("name", result.getName());
        verify(themeRepository, times(1)).findById(id);
        verify(themeRepository, times(1)).save(any(ThemeEntity.class));
        verify(themeMapper, times(1)).toDto(any(ThemeEntity.class));
    }

    @Test
    public void Test3() {
        String id = "12345";
        ThemeDto themeDto = ThemeDto.builder().name("name").build();
        when(themeRepository.findById(id)).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    themeService.updateTheme(id, themeDto);
                });
        assertEquals("Theme not found!", notFoundException.getMessage());

        verify(themeRepository, times(1)).findById(id);
        verify(themeRepository, times(0)).save(any(ThemeEntity.class));
    }

    @Test
    public void Test4() {
        String id = "12345";
        ThemeEntity themeEntity = ThemeEntity.builder().id("12345").name("name").build();
        when(themeRepository.findById(id)).thenReturn(Optional.of(themeEntity));
        doNothing().when(themeRepository).delete(themeEntity);
        themeService.deleteTheme(id);
        verify(themeRepository, times(1)).findById(id);
        verify(themeRepository, times(1)).delete(themeEntity);
    }

    @Test
    public void Test5() {
        String id = "12345";
        when(themeRepository.findById(id)).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    themeService.deleteTheme(id);
                });
        assertEquals("Theme not found!", notFoundException.getMessage());
        verify(themeRepository, times(1)).findById(id);
        verify(themeRepository, times(0)).delete(any(ThemeEntity.class));
    }

    @Test
    public void Test6() {
        String id = "12345";
        ThemeEntity themeEntity = ThemeEntity.builder().id("12345").name("name").build();
        ThemeDto outputDto = ThemeDto.builder().id("12345").name("name").build();
        when(themeRepository.findById(id)).thenReturn(Optional.of(themeEntity));
        when(themeMapper.toDto(any(ThemeEntity.class))).thenReturn(outputDto);
        ThemeDto result = themeService.getThemeById(id);
        assertNotNull(result);
        assertEquals("12345", result.getId());
        assertEquals("name", result.getName());

        verify(themeRepository, times(1)).findById(id);
        verify(themeMapper, times(1)).toDto(any(ThemeEntity.class));
    }

    @Test
    public void Test7() {
        String id = "12345";
        when(themeRepository.findById(id)).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    themeService.getThemeById(id);
                });
        assertEquals("Theme not found!", notFoundException.getMessage());
        verify(themeRepository, times(1)).findById(id);
        verify(themeMapper, times(0)).toDto(any(ThemeEntity.class));
    }

    @Test
    public void Test8() {
        ThemeEntity themeEntity1 = ThemeEntity.builder().id("1").name("10").build();
        ThemeEntity themeEntity2 = ThemeEntity.builder().id("2").name("20").build();
        ThemeDto themeDto1 = ThemeDto.builder().id("1").name("10").build();
        ThemeDto themeDto2 = ThemeDto.builder().id("2").name("20").build();
        when(themeRepository.findAll()).thenReturn(List.of(themeEntity1, themeEntity2));
        when(themeMapper.toDto(themeEntity1)).thenReturn(themeDto1);
        when(themeMapper.toDto(themeEntity2)).thenReturn(themeDto2);
        List<ThemeDto> result = themeService.getAllTheme();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("2", result.get(1).getId());
        assertEquals("10", result.get(0).getName());
        assertEquals("20", result.get(1).getName());

        verify(themeRepository, times(1)).findAll();
        verify(themeMapper, times(2)).toDto(any(ThemeEntity.class));
    }
}
