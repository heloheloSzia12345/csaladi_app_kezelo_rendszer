package org.example.csaladi_app_kezelo_rendszer.mapperTest;

import org.example.csaladi_app_kezelo_rendszer.dto.ApplicationDto;
import org.example.csaladi_app_kezelo_rendszer.entity.ApplicationEntity;
import org.example.csaladi_app_kezelo_rendszer.entity.IconEntity;
import org.example.csaladi_app_kezelo_rendszer.mapper.ApplicationMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ApplicationMapperTest {
    @Autowired
    private ApplicationMapper applicationMapper;

    @Test
    public void Test1() {
        assertNull(applicationMapper.iconsToIconsId(null));
    }

    @Test
    public void Test2() {
        IconEntity icon = IconEntity.builder().id("12345").build();
        assertEquals(applicationMapper.iconsToIconsId(List.of(icon)), List.of("12345"));
    }

    @Test
    public void Test3() {
        ApplicationEntity applicationEntity = null;
        ApplicationDto applicationDto = applicationMapper.toDto(applicationEntity);
        assertNull(applicationDto);
    }

    @Test
    public void Test4() {
        IconEntity iconEntity1 = IconEntity.builder().id("1").name("10").build();
        IconEntity iconEntity2 = IconEntity.builder().id("2").name("20").build();
        ApplicationEntity applicationEntity = ApplicationEntity.builder().id("12345").name("name").icons(List.of(iconEntity1, iconEntity2)).build();
        ApplicationDto applicationDto = applicationMapper.toDto(applicationEntity);
        assertNotNull(applicationDto);
        assertEquals("name", applicationDto.getName());
        assertEquals("12345", applicationDto.getId());
        assertEquals("1", iconEntity1.getId());
        assertEquals("2", iconEntity2.getId());
    }

    @Test
    public void Test5() {
        ApplicationDto applicationDto = null;
        ApplicationEntity applicationEntity = applicationMapper.toEntity(applicationDto);
        assertNull(applicationEntity);
    }

    @Test
    public void Test6() {
        ApplicationDto applicationDto = ApplicationDto.builder().id("12345").name("name").iconsId(List.of("1", "2")).build();
        ApplicationEntity applicationEntity = applicationMapper.toEntity(applicationDto);
        assertNotNull(applicationEntity);
        assertEquals("name", applicationEntity.getName());
        assertEquals("12345", applicationEntity.getId());
    }

    @Test
    public void Test7() {
        ApplicationDto applicationDto = null;
        IconEntity iconEntity1 = IconEntity.builder().id("1").name("10").build();
        IconEntity iconEntity2 = IconEntity.builder().id("2").name("20").build();
        ApplicationEntity applicationEntity = ApplicationEntity.builder().id("12345").name("name").icons(List.of(iconEntity1, iconEntity2)).build();
        applicationMapper.updateEntityFromDto(applicationDto, applicationEntity);
        assertNotNull(applicationEntity);
        assertEquals("name", applicationEntity.getName());
        assertEquals("12345", applicationEntity.getId());
    }

    @Test
    public void Test8() {
        ApplicationDto applicationDto = ApplicationDto.builder().name("name1").build();
        IconEntity iconEntity1 = IconEntity.builder().id("1").name("10").build();
        IconEntity iconEntity2 = IconEntity.builder().id("2").name("20").build();
        ApplicationEntity applicationEntity = ApplicationEntity.builder().id("12345").name("name").icons(List.of(iconEntity1, iconEntity2)).build();
        applicationMapper.updateEntityFromDto(applicationDto, applicationEntity);
        assertNotNull(applicationEntity);
        assertEquals("name1", applicationEntity.getName());
        assertEquals("12345", applicationEntity.getId());
    }
}
