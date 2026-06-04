package org.example.csaladi_app_kezelo_rendszer.serviceTest;

import jakarta.persistence.EntityNotFoundException;
import org.example.csaladi_app_kezelo_rendszer.dto.ApplicationDto;
import org.example.csaladi_app_kezelo_rendszer.entity.ApplicationEntity;
import org.example.csaladi_app_kezelo_rendszer.mapper.ApplicationMapper;
import org.example.csaladi_app_kezelo_rendszer.repository.ApplicationRepository;
import org.example.csaladi_app_kezelo_rendszer.service.ApplicationService;
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
public class ApplicationServiceTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    ApplicationMapper applicationMapper;

    @InjectMocks
    ApplicationService applicationService;

    @Test
    public void Test1() {
        ApplicationDto applicationDto = ApplicationDto.builder().name("name").build();
        ApplicationDto outputDto = ApplicationDto.builder().id("12345").name("name").build();
        ApplicationEntity applicationEntity = ApplicationEntity.builder().id("12345").name("name").build();
        when(applicationRepository.save(any(ApplicationEntity.class))).thenReturn(applicationEntity);
        when(applicationMapper.toDto(applicationEntity)).thenReturn(outputDto);
        ApplicationDto result = applicationService.createApplication(applicationDto);
        assertNotNull(result);
        assertEquals("12345", result.getId());
        assertEquals("name", result.getName());
        verify(applicationRepository, times(1)).save(any(ApplicationEntity.class));
        verify(applicationMapper, times(1)).toDto(any(ApplicationEntity.class));
    }

    @Test
    public void Test2() {
        String id = "12345";
        ApplicationDto applicationDto = ApplicationDto.builder().name("name").build();
        ApplicationEntity applicationEntity = ApplicationEntity.builder().id("12345").name("change").build();
        ApplicationDto outputDto = ApplicationDto.builder().id("12345").name("name").build();
        ApplicationEntity outputEntity = ApplicationEntity.builder().id("12345").name("name").build();
        when(applicationRepository.findById(id)).thenReturn(Optional.of(applicationEntity));
        when(applicationRepository.save(any(ApplicationEntity.class))).thenReturn(outputEntity);
        when(applicationMapper.toDto(any(ApplicationEntity.class))).thenReturn(outputDto);
        ApplicationDto result = applicationService.updateApplication(id, applicationDto);
        assertNotNull(result);
        assertEquals("12345", result.getId());
        assertEquals("name", result.getName());
        verify(applicationRepository, times(1)).findById(id);
        verify(applicationRepository, times(1)).save(any(ApplicationEntity.class));
        verify(applicationMapper, times(1)).toDto(any(ApplicationEntity.class));
    }

    @Test
    public void Test3() {
        String id = "12345";
        ApplicationDto applicationDto = ApplicationDto.builder().name("name").build();
        when(applicationRepository.findById(id)).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    applicationService.updateApplication(id, applicationDto);
                });
        assertEquals("Application not found!", notFoundException.getMessage());

        verify(applicationRepository, times(1)).findById(id);
        verify(applicationRepository, times(0)).save(any(ApplicationEntity.class));
    }

    @Test
    public void Test4() {
        String id = "12345";
        ApplicationEntity applicationEntity = ApplicationEntity.builder().id("12345").name("name").build();
        when(applicationRepository.findById(id)).thenReturn(Optional.of(applicationEntity));
        doNothing().when(applicationRepository).delete(applicationEntity);
        applicationService.deleteApplication(id);
        verify(applicationRepository, times(1)).findById(id);
        verify(applicationRepository, times(1)).delete(applicationEntity);
    }

    @Test
    public void Test5() {
        String id = "12345";
        when(applicationRepository.findById(id)).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    applicationService.deleteApplication(id);
                });
        assertEquals("Application not found!", notFoundException.getMessage());
        verify(applicationRepository, times(1)).findById(id);
        verify(applicationRepository, times(0)).delete(any(ApplicationEntity.class));
    }

    @Test
    public void Test6() {
        String id = "12345";
        ApplicationEntity applicationEntity = ApplicationEntity.builder().id("12345").name("name").build();
        ApplicationDto outputDto = ApplicationDto.builder().id("12345").name("name").build();
        when(applicationRepository.findById(id)).thenReturn(Optional.of(applicationEntity));
        when(applicationMapper.toDto(any(ApplicationEntity.class))).thenReturn(outputDto);
        ApplicationDto result = applicationService.getApplicationById(id);
        assertNotNull(result);
        assertEquals("12345", result.getId());
        assertEquals("name", result.getName());

        verify(applicationRepository, times(1)).findById(id);
        verify(applicationMapper, times(1)).toDto(any(ApplicationEntity.class));
    }

    @Test
    public void Test7() {
        String id = "12345";
        when(applicationRepository.findById(id)).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    applicationService.getApplicationById(id);
                });
        assertEquals("Application not found!", notFoundException.getMessage());
        verify(applicationRepository, times(1)).findById(id);
        verify(applicationMapper, times(0)).toDto(any(ApplicationEntity.class));
    }

    @Test
    public void Test8() {
        ApplicationEntity applicationEntity1 = ApplicationEntity.builder().id("1").name("10").build();
        ApplicationEntity applicationEntity2 = ApplicationEntity.builder().id("2").name("20").build();
        ApplicationDto applicationDto1 = ApplicationDto.builder().id("1").name("10").build();
        ApplicationDto applicationDto2 = ApplicationDto.builder().id("2").name("20").build();
        when(applicationRepository.findAll()).thenReturn(List.of(applicationEntity1, applicationEntity2));
        when(applicationMapper.toDto(applicationEntity1)).thenReturn(applicationDto1);
        when(applicationMapper.toDto(applicationEntity2)).thenReturn(applicationDto2);
        List<ApplicationDto> result = applicationService.getAllApplication();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("2", result.get(1).getId());
        assertEquals("10", result.get(0).getName());
        assertEquals("20", result.get(1).getName());

        verify(applicationRepository, times(1)).findAll();
        verify(applicationMapper, times(2)).toDto(any(ApplicationEntity.class));
    }
}
