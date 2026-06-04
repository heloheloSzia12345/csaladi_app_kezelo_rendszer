package org.example.csaladi_app_kezelo_rendszer.serviceTest;

import jakarta.persistence.EntityNotFoundException;
import org.example.csaladi_app_kezelo_rendszer.dto.BackgroundDto;
import org.example.csaladi_app_kezelo_rendszer.entity.BackgroundEntity;
import org.example.csaladi_app_kezelo_rendszer.mapper.BackgroundMapper;
import org.example.csaladi_app_kezelo_rendszer.repository.BackgroundRepository;
import org.example.csaladi_app_kezelo_rendszer.service.BackgroundService;
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
public class BackgroundServiceTest {

    @Mock
    private BackgroundRepository backgroundRepository;

    @Mock
    BackgroundMapper backgroundMapper;

    @InjectMocks
    BackgroundService backgroundService;

    @Test
    public void Test1() {
        BackgroundDto backgroundDto = BackgroundDto.builder().name("name").build();
        BackgroundDto outputDto = BackgroundDto.builder().id("12345").name("name").build();
        BackgroundEntity backgroundEntity = BackgroundEntity.builder().id("12345").name("name").build();
        when(backgroundRepository.save(any(BackgroundEntity.class))).thenReturn(backgroundEntity);
        when(backgroundMapper.toDto(backgroundEntity)).thenReturn(outputDto);
        BackgroundDto result = backgroundService.createBackground(backgroundDto);
        assertNotNull(result);
        assertEquals("12345", result.getId());
        assertEquals("name", result.getName());
        verify(backgroundRepository, times(1)).save(any(BackgroundEntity.class));
        verify(backgroundMapper, times(1)).toDto(any(BackgroundEntity.class));
    }

    @Test
    public void Test2() {
        String id = "12345";
        BackgroundDto backgroundDto = BackgroundDto.builder().name("name").build();
        BackgroundEntity backgroundEntity = BackgroundEntity.builder().id("12345").name("change").build();
        BackgroundDto outputDto = BackgroundDto.builder().id("12345").name("name").build();
        BackgroundEntity outputEntity = BackgroundEntity.builder().id("12345").name("name").build();
        when(backgroundRepository.findById(id)).thenReturn(Optional.of(backgroundEntity));
        when(backgroundRepository.save(any(BackgroundEntity.class))).thenReturn(outputEntity);
        when(backgroundMapper.toDto(any(BackgroundEntity.class))).thenReturn(outputDto);
        BackgroundDto result = backgroundService.updateBackground(id, backgroundDto);
        assertNotNull(result);
        assertEquals("12345", result.getId());
        assertEquals("name", result.getName());
        verify(backgroundRepository, times(1)).findById(id);
        verify(backgroundRepository, times(1)).save(any(BackgroundEntity.class));
        verify(backgroundMapper, times(1)).toDto(any(BackgroundEntity.class));
    }

    @Test
    public void Test3() {
        String id = "12345";
        BackgroundDto backgroundDto = BackgroundDto.builder().name("name").build();
        when(backgroundRepository.findById(id)).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    backgroundService.updateBackground(id, backgroundDto);
                });
        assertEquals("Background not found!", notFoundException.getMessage());

        verify(backgroundRepository, times(1)).findById(id);
        verify(backgroundRepository, times(0)).save(any(BackgroundEntity.class));
    }

    @Test
    public void Test4() {
        String id = "12345";
        BackgroundEntity backgroundEntity = BackgroundEntity.builder().id("12345").name("name").build();
        when(backgroundRepository.findById(id)).thenReturn(Optional.of(backgroundEntity));
        doNothing().when(backgroundRepository).delete(backgroundEntity);
        backgroundService.deleteBackground(id);
        verify(backgroundRepository, times(1)).findById(id);
        verify(backgroundRepository, times(1)).delete(backgroundEntity);
    }

    @Test
    public void Test5() {
        String id = "12345";
        when(backgroundRepository.findById(id)).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    backgroundService.deleteBackground(id);
                });
        assertEquals("Background not found!", notFoundException.getMessage());
        verify(backgroundRepository, times(1)).findById(id);
        verify(backgroundRepository, times(0)).delete(any(BackgroundEntity.class));
    }

    @Test
    public void Test6() {
        String id = "12345";
        BackgroundEntity backgroundEntity = BackgroundEntity.builder().id("12345").name("name").build();
        BackgroundDto outputDto = BackgroundDto.builder().id("12345").name("name").build();
        when(backgroundRepository.findById(id)).thenReturn(Optional.of(backgroundEntity));
        when(backgroundMapper.toDto(any(BackgroundEntity.class))).thenReturn(outputDto);
        BackgroundDto result = backgroundService.getBackgroundById(id);
        assertNotNull(result);
        assertEquals("12345", result.getId());
        assertEquals("name", result.getName());

        verify(backgroundRepository, times(1)).findById(id);
        verify(backgroundMapper, times(1)).toDto(any(BackgroundEntity.class));
    }

    @Test
    public void Test7() {
        String id = "12345";
        when(backgroundRepository.findById(id)).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    backgroundService.getBackgroundById(id);
                });
        assertEquals("Background not found!", notFoundException.getMessage());
        verify(backgroundRepository, times(1)).findById(id);
        verify(backgroundMapper, times(0)).toDto(any(BackgroundEntity.class));
    }

    @Test
    public void Test8() {
        BackgroundEntity backgroundEntity1 = BackgroundEntity.builder().id("1").name("10").build();
        BackgroundEntity backgroundEntity2 = BackgroundEntity.builder().id("2").name("20").build();
        BackgroundDto backgroundDto1 = BackgroundDto.builder().id("1").name("10").build();
        BackgroundDto backgroundDto2 = BackgroundDto.builder().id("2").name("20").build();
        when(backgroundRepository.findAll()).thenReturn(List.of(backgroundEntity1, backgroundEntity2));
        when(backgroundMapper.toDto(backgroundEntity1)).thenReturn(backgroundDto1);
        when(backgroundMapper.toDto(backgroundEntity2)).thenReturn(backgroundDto2);
        List<BackgroundDto> result = backgroundService.getAllBackground();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("2", result.get(1).getId());
        assertEquals("10", result.get(0).getName());
        assertEquals("20", result.get(1).getName());

        verify(backgroundRepository, times(1)).findAll();
        verify(backgroundMapper, times(2)).toDto(any(BackgroundEntity.class));
    }
}
