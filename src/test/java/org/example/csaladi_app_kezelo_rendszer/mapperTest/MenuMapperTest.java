package org.example.csaladi_app_kezelo_rendszer.mapperTest;

import org.example.csaladi_app_kezelo_rendszer.dto.MenuDto;
import org.example.csaladi_app_kezelo_rendszer.entity.IconEntity;
import org.example.csaladi_app_kezelo_rendszer.entity.MenuEntity;
import org.example.csaladi_app_kezelo_rendszer.entity.UserEntity;
import org.example.csaladi_app_kezelo_rendszer.mapper.MenuMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MenuMapperTest {

    @Autowired
    private MenuMapper menuMapper;

    @Test
    public void Test1() {
        assertNull(menuMapper.iconsToIconsId(null));
    }

    @Test
    public void Test2() {
        IconEntity icon = IconEntity.builder().id("12345").build();
        assertEquals(menuMapper.iconsToIconsId(List.of(icon)), List.of("12345"));
    }

    @Test
    public void Test3() {
        MenuEntity menuEntity = null;
        MenuDto menuDto = menuMapper.toDto(menuEntity);
        assertNull(menuDto);
    }

    @Test
    public void Test4() {
        UserEntity user = UserEntity.builder().id("1").name("10").build();
        IconEntity icon1 = IconEntity.builder().id("1").name("10").build();
        IconEntity icon2 = IconEntity.builder().id("2").name("20").build();
        MenuEntity menuEntity = MenuEntity.builder().id("12345").name("name").user(user).icons(List.of(icon1, icon2)).build();
        MenuDto menuDto = menuMapper.toDto(menuEntity);
        assertNotNull(menuDto);
        assertEquals("name", menuDto.getName());
        assertEquals("12345", menuDto.getId());
        assertEquals("1", menuDto.getUserId());
        assertEquals(List.of("1", "2"), menuDto.getIconsId());
    }

    @Test
    public void Test5() {
        MenuDto menuDto = null;
        MenuEntity menuEntity = menuMapper.toEntity(menuDto);
        assertNull(menuEntity);
    }

    @Test
    public void Test6() {
        MenuDto menuDto = MenuDto.builder().id("12345").name("name").userId("1").iconsId(List.of("1", "2")).build();
        MenuEntity menuEntity = menuMapper.toEntity(menuDto);
        assertNotNull(menuEntity);
        assertEquals("name", menuEntity.getName());
        assertEquals("12345", menuEntity.getId());
        assertEquals("1", menuEntity.getUser().getId());
    }

    @Test
    public void Test7() {
        MenuDto menuDto = null;
        UserEntity user = UserEntity.builder().id("1").name("10").build();
        IconEntity icon1 = IconEntity.builder().id("1").name("10").build();
        IconEntity icon2 = IconEntity.builder().id("2").name("20").build();
        MenuEntity menuEntity = MenuEntity.builder().id("12345").name("name").user(user).icons(List.of(icon1, icon2)).build();
        menuMapper.updateEntityFromDto(menuDto, menuEntity);
        assertNotNull(menuEntity);
        assertEquals("name", menuEntity.getName());
        assertEquals("12345", menuEntity.getId());
    }

    @Test
    public void Test8() {
        MenuDto menuDto = MenuDto.builder().name("name1").userId("10").build();
        UserEntity user = UserEntity.builder().id("1").name("10").build();
        IconEntity icon1 = IconEntity.builder().id("1").name("10").build();
        IconEntity icon2 = IconEntity.builder().id("2").name("20").build();
        MenuEntity menuEntity = MenuEntity.builder().id("12345").name("name").user(user).icons(List.of(icon1, icon2)).build();
        menuMapper.updateEntityFromDto(menuDto, menuEntity);
        assertNotNull(menuEntity);
        assertEquals("name1", menuEntity.getName());
        assertEquals("12345", menuEntity.getId());
        assertEquals("10", menuEntity.getUser().getId());
    }

    @Test
    public void Test9() {
        MenuEntity menuEntity = MenuEntity.builder().id("12345").name("name").user(null).icons(null).build();
        MenuDto menuDto = menuMapper.toDto(menuEntity);
        assertNotNull(menuDto);
        assertNull(menuDto.getUserId());
        assertNull(menuDto.getIconsId());
    }

    @Test
    public void Test10() {
        UserEntity user = UserEntity.builder().id(null).name("10").build();
        MenuEntity menuEntity = MenuEntity.builder().id("12345").name("name").user(user).icons(null).build();
        MenuDto menuDto = menuMapper.toDto(menuEntity);
        assertNotNull(menuDto);
        assertNull(menuDto.getUserId());
    }

    @Test
    public void Test11() {
        MenuEntity menuEntity = MenuEntity.builder().id("12345").name("name").user(null).build();
        MenuDto menuDto = MenuDto.builder().name("name1").userId("10").build();
        menuMapper.updateEntityFromDto(menuDto, menuEntity);
        assertNotNull(menuEntity.getUser());
        assertEquals("10", menuEntity.getUser().getId());
    }

    @Test
    public void Test12() throws Exception {
        Class<?> implClass = menuMapper.getClass();

        Method userMethod = implClass.getDeclaredMethod("menuEntityUserId", MenuEntity.class);
        userMethod.setAccessible(true);
        assertNull(userMethod.invoke(menuMapper, (MenuEntity) null));

        Method menuDtoToUserEntityMethod = implClass.getDeclaredMethod("menuDtoToUserEntity", MenuDto.class);
        Method menuDtoToUserEntity1Method = implClass.getDeclaredMethod("menuDtoToUserEntity1", MenuDto.class, UserEntity.class);

        menuDtoToUserEntityMethod.setAccessible(true);
        menuDtoToUserEntity1Method.setAccessible(true);

        assertNull(menuDtoToUserEntityMethod.invoke(menuMapper, (MenuDto) null));

        UserEntity userObj = new UserEntity();
        menuDtoToUserEntity1Method.invoke(menuMapper, (MenuDto) null, userObj);
    }
}
