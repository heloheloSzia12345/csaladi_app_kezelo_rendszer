package org.example.csaladi_app_kezelo_rendszer.mapperTest;

import org.example.csaladi_app_kezelo_rendszer.dto.ThemeDto;
import org.example.csaladi_app_kezelo_rendszer.dto.UserDto;
import org.example.csaladi_app_kezelo_rendszer.entity.ThemeEntity;
import org.example.csaladi_app_kezelo_rendszer.entity.UserEntity;
import org.example.csaladi_app_kezelo_rendszer.mapper.ThemeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ThemeMapperTest {
    @Autowired
    private ThemeMapper themeMapper;

    @Test
    public void Test1() {
        assertNull(themeMapper.usersToUsersId(null));
    }

    @Test
    public void Test2() {
        UserEntity user = UserEntity.builder().id("12345").build();
        assertEquals(themeMapper.usersToUsersId(List.of(user)), List.of("12345"));
    }

    @Test
    public void Test3() {
        ThemeEntity themeEntity = null;
        ThemeDto themeDto = themeMapper.toDto(themeEntity);
        assertNull(themeDto);
    }

    @Test
    public void Test4() {
        UserEntity userEntity1 = UserEntity.builder().id("1").name("10").theme(null).background(null).menu(null).build();
        UserEntity userEntity2 = UserEntity.builder().id("2").name("20").theme(null).background(null).menu(null).build();
        ThemeEntity themeEntity = ThemeEntity.builder().id("12345").name("name").users(List.of(userEntity1, userEntity2)).build();
        ThemeDto themeDto = themeMapper.toDto(themeEntity);
        assertNotNull(themeDto);
        assertEquals("name", themeDto.getName());
        assertEquals("12345", themeDto.getId());
        assertEquals("1", userEntity1.getId());
        assertEquals("2", userEntity2.getId());
    }

    @Test
    public void Test5() {
        ThemeDto themeDto = null;
        ThemeEntity themeEntity = themeMapper.toEntity(themeDto);
        assertNull(themeEntity);
    }

    @Test
    public void Test6() {
        UserDto userDto1 = UserDto.builder().id("1").name("10").themeId(null).backgroundId(null).menuId(null).build();
        UserDto userDto2 = UserDto.builder().id("2").name("20").themeId(null).backgroundId(null).menuId(null).build();
        ThemeDto themeDto = ThemeDto.builder().id("12345").name("name").usersId(List.of(userDto1.getId(), userDto2.getId())).build();
        ThemeEntity themeEntity = themeMapper.toEntity(themeDto);
        assertNotNull(themeDto);
        assertEquals("name", themeEntity.getName());
        assertEquals("12345", themeEntity.getId());
        assertEquals("1", userDto1.getId());
        assertEquals("2", userDto2.getId());
    }

    @Test
    public void Test7() {
        ThemeDto themeDto = null;
        UserEntity userEntity1 = UserEntity.builder().id("1").name("10").theme(null).background(null).menu(null).build();
        UserEntity userEntity2 = UserEntity.builder().id("2").name("20").theme(null).background(null).menu(null).build();
        ThemeEntity themeEntity = ThemeEntity.builder().id("12345").name("name").users(List.of(userEntity1, userEntity2)).build();
        themeMapper.updateEntityFromDto(themeDto, themeEntity);
        assertNotNull(themeEntity);
        assertEquals("name", themeEntity.getName());
        assertEquals("12345", themeEntity.getId());
        assertEquals("1", userEntity1.getId());
        assertEquals("2", userEntity2.getId());
    }

    @Test
    public void Test8() {
        ThemeDto themeDto = ThemeDto.builder().name("name1").build();
        UserEntity userEntity1 = UserEntity.builder().id("1").name("10").theme(null).background(null).menu(null).build();
        UserEntity userEntity2 = UserEntity.builder().id("2").name("20").theme(null).background(null).menu(null).build();
        ThemeEntity themeEntity = ThemeEntity.builder().id("12345").name("name").users(List.of(userEntity1, userEntity2)).build();
        themeMapper.updateEntityFromDto(themeDto, themeEntity);
        assertNotNull(themeEntity);
        assertEquals("name1", themeEntity.getName());
        assertEquals("12345", themeEntity.getId());
    }
}
