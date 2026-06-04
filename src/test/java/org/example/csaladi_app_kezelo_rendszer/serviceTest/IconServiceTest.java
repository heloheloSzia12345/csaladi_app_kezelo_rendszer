package org.example.csaladi_app_kezelo_rendszer.serviceTest;

import jakarta.persistence.EntityNotFoundException;
import org.example.csaladi_app_kezelo_rendszer.dto.IconDto;
import org.example.csaladi_app_kezelo_rendszer.entity.ApplicationEntity;
import org.example.csaladi_app_kezelo_rendszer.entity.IconEntity;
import org.example.csaladi_app_kezelo_rendszer.entity.MenuEntity;
import org.example.csaladi_app_kezelo_rendszer.mapper.IconMapper;
import org.example.csaladi_app_kezelo_rendszer.repository.ApplicationRepository;
import org.example.csaladi_app_kezelo_rendszer.repository.IconRepository;
import org.example.csaladi_app_kezelo_rendszer.repository.MenuRepository;
import org.example.csaladi_app_kezelo_rendszer.service.IconService;
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
public class IconServiceTest {
    @Mock
    private IconRepository iconRepository;

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private IconMapper iconMapper;

    @InjectMocks
    private IconService iconService;

    @Test
    public void Test1() {
        IconDto iconDto = IconDto.builder().name("name").applicationId("app").menuId("menu").build();
        ApplicationEntity applicationEntity = ApplicationEntity.builder().id("app").build();
        MenuEntity menuEntity = MenuEntity.builder().id("menu").build();
        IconEntity iconEntity = IconEntity.builder().id("1").name("name").application(applicationEntity).menu(menuEntity).build();
        IconDto outputDto = IconDto.builder().id("1").name("name").applicationId("app").menuId("menu").build();

        when(applicationRepository.findById("app")).thenReturn(Optional.of(applicationEntity));
        when(menuRepository.findById("menu")).thenReturn(Optional.of(menuEntity));
        when(iconRepository.save(any(IconEntity.class))).thenReturn(iconEntity);
        when(iconMapper.toDto(any(IconEntity.class))).thenReturn(outputDto);
        IconDto result = iconService.createIcon(iconDto);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("name", result.getName());
        verify(applicationRepository, times(1)).findById("app");
        verify(menuRepository, times(1)).findById("menu");
        verify(iconMapper, times(1)).toDto(any(IconEntity.class));
        verify(iconRepository, times(1)).save(any(IconEntity.class));
    }

    @Test
    public void Test2() {
        IconDto iconDto = IconDto.builder().name("name").applicationId("app").build();
        when(applicationRepository.findById("app")).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    iconService.createIcon(iconDto);
                });
        assertEquals("Application not found!", notFoundException.getMessage());
        verify(applicationRepository, times(1)).findById("app");
    }

    @Test
    public void Test3() {
        IconDto iconDto = IconDto.builder().name("name").applicationId("app").menuId("menu").build();
        ApplicationEntity applicationEntity = ApplicationEntity.builder().id("app").build();
        when(applicationRepository.findById("app")).thenReturn(Optional.of(applicationEntity));
        when(menuRepository.findById("menu")).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    iconService.createIcon(iconDto);
                });
        assertEquals("Menu not found!", notFoundException.getMessage());
        verify(applicationRepository, times(1)).findById("app");
        verify(menuRepository, times(1)).findById("menu");
    }

    @Test
    public void Test4() {
        IconDto iconDto = IconDto.builder().name("name").applicationId("app").menuId(null).build();
        ApplicationEntity applicationEntity = ApplicationEntity.builder().id("app").build();
        IconEntity iconEntity = IconEntity.builder().id("1").name("name").application(applicationEntity).menu(null).build();
        IconDto outputDto = IconDto.builder().id("1").name("name").applicationId("app").menuId(null).build();

        when(applicationRepository.findById("app")).thenReturn(Optional.of(applicationEntity));
        when(iconRepository.save(any(IconEntity.class))).thenReturn(iconEntity);
        when(iconMapper.toDto(any(IconEntity.class))).thenReturn(outputDto);
        IconDto result = iconService.createIcon(iconDto);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("name", result.getName());
        assertNull(result.getMenuId());

        verify(applicationRepository, times(1)).findById("app");
        verify(menuRepository, never()).findById(any());
        verify(iconRepository, times(1)).save(any(IconEntity.class));
    }

    @Test
    public void Test5() {
        String id = "1";
        IconDto iconDto = IconDto.builder().name("name1").applicationId("app").menuId("menu").build();
        IconEntity iconExistingEntity = IconEntity.builder().id(id).name("name").application(null).menu(null).build();
        ApplicationEntity applicationEntity = ApplicationEntity.builder().id("app").build();
        MenuEntity menuEntity = MenuEntity.builder().id("menu").build();
        IconEntity iconEntity = IconEntity.builder().id("1").name("name1").application(applicationEntity).menu(menuEntity).build();
        IconDto outputDto = IconDto.builder().id("1").name("name1").applicationId("app").menuId("menu").build();

        when(applicationRepository.findById("app")).thenReturn(Optional.of(applicationEntity));
        when(menuRepository.findById("menu")).thenReturn(Optional.of(menuEntity));
        when(iconRepository.findById(id)).thenReturn(Optional.of(iconExistingEntity));
        when(iconMapper.toDto(any(IconEntity.class))).thenReturn(outputDto);
        when(iconRepository.save(any(IconEntity.class))).thenReturn(iconEntity);

        IconDto result = iconService.updateIcon(id, iconDto);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("name1", result.getName());

        verify(applicationRepository, times(1)).findById("app");
        verify(menuRepository, times(1)).findById("menu");
        verify(iconMapper, times(1)).toDto(any(IconEntity.class));
        verify(iconRepository, times(1)).save(any(IconEntity.class));
    }

    @Test
    public void Test6() {
        String id = "1";
        IconDto iconDto = IconDto.builder().name("name").applicationId("app").menuId("menu").build();
        when(iconRepository.findById(id)).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    iconService.updateIcon(id, iconDto);
                });
        assertEquals("Icon not found!", notFoundException.getMessage());
        verify(iconRepository, times(1)).findById("1");
    }

    @Test
    public void Test7() {
        IconDto iconDto = IconDto.builder().name("name").applicationId("app").build();
        String id = "1";
        IconEntity iconExistingEntity = IconEntity.builder().id(id).name("name").application(null).menu(null).build();
        when(iconRepository.findById(id)).thenReturn(Optional.of(iconExistingEntity));
        when(applicationRepository.findById("app")).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    iconService.updateIcon(id, iconDto);
                });
        assertEquals("Application not found!", notFoundException.getMessage());
        verify(applicationRepository, times(1)).findById("app");
    }

    @Test
    public void Test8() {
        String id = "1";
        IconDto iconDto = IconDto.builder().name("name").applicationId(null).menuId(null).build();
        IconEntity iconExistingEntity = IconEntity.builder().id(id).name("name1").application(null).menu(null).build();
        IconEntity iconSavedEntity = IconEntity.builder().id(id).name("name").application(null).menu(null).build();
        IconDto outputDto = IconDto.builder().id(id).name("name").applicationId(null).menuId(null).build();

        when(iconRepository.findById(id)).thenReturn(Optional.of(iconExistingEntity));
        when(iconRepository.save(any(IconEntity.class))).thenReturn(iconSavedEntity);
        when(iconMapper.toDto(any(IconEntity.class))).thenReturn(outputDto);

        IconDto result = iconService.updateIcon(id, iconDto);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("name", result.getName());
        assertNull(result.getApplicationId());
        assertNull(result.getMenuId());

        verify(iconRepository, times(1)).findById(id);
        verify(applicationRepository, never()).findById(any());
        verify(menuRepository, never()).findById(any());
        verify(iconRepository, times(1)).save(any(IconEntity.class));
        verify(iconMapper, times(1)).toDto(any(IconEntity.class));
    }

    @Test
    public void Test9() {
        String id = "1";
        IconDto iconDto = IconDto.builder().name("name").applicationId("app").menuId("menu").build();
        ApplicationEntity applicationEntity = ApplicationEntity.builder().id("app").build();
        IconEntity iconExistingEntity = IconEntity.builder().id(id).name("name1").application(null).menu(null).build();
        when(iconRepository.findById(id)).thenReturn(Optional.of(iconExistingEntity));
        when(applicationRepository.findById("app")).thenReturn(Optional.of(applicationEntity));
        when(menuRepository.findById("menu")).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    iconService.updateIcon(id, iconDto);
                });
        assertEquals("Menu not found!", notFoundException.getMessage());
        verify(applicationRepository, times(1)).findById("app");
        verify(menuRepository, times(1)).findById("menu");
    }

    @Test
    public void Test10() {
        String id = "12345";
        IconEntity iconEntity = IconEntity.builder().id("1").name("name").application(null).menu(null).build();
        when(iconRepository.findById(id)).thenReturn(Optional.of(iconEntity));
        doNothing().when(iconRepository).delete(iconEntity);
        iconService.deleteIcon(id);
        verify(iconRepository, times(1)).findById(id);
        verify(iconRepository, times(1)).delete(iconEntity);
    }

    @Test
    public void Test11() {
        String id = "12345";
        when(iconRepository.findById(id)).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    iconService.deleteIcon(id);
                });
        assertEquals("Icon not found!", notFoundException.getMessage());
        verify(iconRepository, times(1)).findById(id);
        verify(iconRepository, times(0)).delete(any(IconEntity.class));
    }

    @Test
    public void Test12() {
        String id = "12345";
        IconEntity iconEntity = IconEntity.builder().id("12345").name("name").application(null).menu(null).build();
        IconDto outputDto = IconDto.builder().id(id).name("name").applicationId(null).menuId(null).build();
        when(iconRepository.findById(id)).thenReturn(Optional.of(iconEntity));
        when(iconMapper.toDto(any(IconEntity.class))).thenReturn(outputDto);
        IconDto result = iconService.getIconById(id);
        assertNotNull(result);
        assertEquals("12345", result.getId());
        assertEquals("name", result.getName());
        verify(iconRepository, times(1)).findById(id);
        verify(iconMapper, times(1)).toDto(any(IconEntity.class));
    }

    @Test
    public void Test13() {
        String id = "12345";
        when(iconRepository.findById(id)).thenReturn(Optional.empty());
        EntityNotFoundException notFoundException = assertThrows(EntityNotFoundException.class,
                () -> {
                    iconService.getIconById(id);
                });
        assertEquals("Icon not found!", notFoundException.getMessage());
        verify(iconRepository, times(1)).findById(id);
        verify(iconMapper, times(0)).toDto(any(IconEntity.class));
    }

    @Test
    public void Test14() {
        IconEntity iconEntity1 = IconEntity.builder().id("1").name("10").application(null).menu(null).build();
        IconEntity iconEntity2 = IconEntity.builder().id("2").name("20").application(null).menu(null).build();
        IconDto iconDto1 = IconDto.builder().id("1").name("10").build();
        IconDto iconDto2 = IconDto.builder().id("2").name("20").build();

        when(iconRepository.findAll()).thenReturn(List.of(iconEntity1, iconEntity2));
        when(iconMapper.toDto(iconEntity1)).thenReturn(iconDto1);
        when(iconMapper.toDto(iconEntity2)).thenReturn(iconDto2);

        List<IconDto> result = iconService.getAllIcon();
        assertEquals(2, result.size());

        assertEquals("1", result.get(0).getId());
        assertEquals("2", result.get(1).getId());
        assertEquals("10", result.get(0).getName());
        assertEquals("20", result.get(1).getName());

        verify(iconRepository, times(1)).findAll();
        verify(iconMapper, times(2)).toDto(any(IconEntity.class));
    }
}
