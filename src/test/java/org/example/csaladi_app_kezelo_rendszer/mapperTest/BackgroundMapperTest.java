package org.example.csaladi_app_kezelo_rendszer.mapperTest;

import org.example.csaladi_app_kezelo_rendszer.dto.BackgroundDto;
import org.example.csaladi_app_kezelo_rendszer.dto.UserDto;
import org.example.csaladi_app_kezelo_rendszer.entity.BackgroundEntity;
import org.example.csaladi_app_kezelo_rendszer.entity.UserEntity;
import org.example.csaladi_app_kezelo_rendszer.mapper.BackgroundMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BackgroundMapperTest {
    @Autowired
    private BackgroundMapper backgroundMapper;

    @Test
    public void Test1() {
        assertNull(backgroundMapper.usersToUsersId(null));
    }

    @Test
    public void Test2() {
        UserEntity user = UserEntity.builder().id("12345").build();
        assertEquals(backgroundMapper.usersToUsersId(List.of(user)), List.of("12345"));
    }

    @Test
    public void Test3() {
        BackgroundEntity backgroundEntity = null;
        BackgroundDto backgroundDto = backgroundMapper.toDto(backgroundEntity);
        assertNull(backgroundDto);
    }

    @Test
    public void Test4() {
        UserEntity userEntity1 = UserEntity.builder().id("1").name("10").theme(null).background(null).menu(null).build();
        UserEntity userEntity2 = UserEntity.builder().id("2").name("20").theme(null).background(null).menu(null).build();
        BackgroundEntity backgroundEntity = BackgroundEntity.builder().id("12345").name("name").users(List.of(userEntity1, userEntity2)).build();
        BackgroundDto backgroundDto = backgroundMapper.toDto(backgroundEntity);
        assertNotNull(backgroundDto);
        assertEquals("name", backgroundDto.getName());
        assertEquals("12345", backgroundDto.getId());
        assertEquals("1", userEntity1.getId());
        assertEquals("2", userEntity2.getId());
    }

    @Test
    public void Test5() {
        BackgroundDto backgroundDto = null;
        BackgroundEntity backgroundEntity = backgroundMapper.toEntity(backgroundDto);
        assertNull(backgroundEntity);
    }

    @Test
    public void Test6() {
        UserDto userDto1 = UserDto.builder().id("1").name("10").themeId(null).backgroundId(null).menuId(null).build();
        UserDto userDto2 = UserDto.builder().id("2").name("20").themeId(null).backgroundId(null).menuId(null).build();
        BackgroundDto backgroundDto = BackgroundDto.builder().id("12345").name("name").usersId(List.of(userDto1.getId(), userDto2.getId())).build();
        BackgroundEntity backgroundEntity = backgroundMapper.toEntity(backgroundDto);
        assertNotNull(backgroundDto);
        assertEquals("name", backgroundEntity.getName());
        assertEquals("12345", backgroundEntity.getId());
        assertEquals("1", userDto1.getId());
        assertEquals("2", userDto2.getId());
    }

    @Test
    public void Test7() {
        BackgroundDto backgroundDto = null;
        UserEntity userEntity1 = UserEntity.builder().id("1").name("10").theme(null).background(null).menu(null).build();
        UserEntity userEntity2 = UserEntity.builder().id("2").name("20").theme(null).background(null).menu(null).build();
        BackgroundEntity backgroundEntity = BackgroundEntity.builder().id("12345").name("name").users(List.of(userEntity1, userEntity2)).build();
        backgroundMapper.updateEntityFromDto(backgroundDto, backgroundEntity);
        assertNotNull(backgroundEntity);
        assertEquals("name", backgroundEntity.getName());
        assertEquals("12345", backgroundEntity.getId());
        assertEquals("1", userEntity1.getId());
        assertEquals("2", userEntity2.getId());
    }

    @Test
    public void Test8() {
        BackgroundDto backgroundDto = BackgroundDto.builder().name("name1").build();
        UserEntity userEntity1 = UserEntity.builder().id("1").name("10").theme(null).background(null).menu(null).build();
        UserEntity userEntity2 = UserEntity.builder().id("2").name("20").theme(null).background(null).menu(null).build();
        BackgroundEntity backgroundEntity = BackgroundEntity.builder().id("12345").name("name").users(List.of(userEntity1, userEntity2)).build();
        backgroundMapper.updateEntityFromDto(backgroundDto, backgroundEntity);
        assertNotNull(backgroundEntity);
        assertEquals("name1", backgroundEntity.getName());
        assertEquals("12345", backgroundEntity.getId());
    }
}
