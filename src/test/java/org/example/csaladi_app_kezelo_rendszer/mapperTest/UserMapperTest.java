package org.example.csaladi_app_kezelo_rendszer.mapperTest;

import org.example.csaladi_app_kezelo_rendszer.dto.UserDto;
import org.example.csaladi_app_kezelo_rendszer.entity.BackgroundEntity;
import org.example.csaladi_app_kezelo_rendszer.entity.MenuEntity;
import org.example.csaladi_app_kezelo_rendszer.entity.ThemeEntity;
import org.example.csaladi_app_kezelo_rendszer.entity.UserEntity;
import org.example.csaladi_app_kezelo_rendszer.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void Test1() {
        ThemeEntity theme = new ThemeEntity();
        theme.setId("1");
        BackgroundEntity background = new BackgroundEntity();
        background.setId("2");
        MenuEntity menu = new MenuEntity();
        menu.setId("3");
        UserEntity userEntity = UserEntity.builder().id("12345").name("name").theme(theme).background(background).menu(menu).build();
        UserDto userDto = userMapper.toDto(userEntity);
        assertEquals(userDto.getId(), "12345");
        assertEquals(userDto.getName(), "name");
        assertEquals(userDto.getThemeId(), "1");
        assertEquals(userDto.getBackgroundId(), "2");
        assertEquals(userDto.getMenuId(), "3");
        assertNotNull(userDto);
    }

    @Test
    public void Test2() {
        UserEntity userEntity = null;
        UserDto userDto = userMapper.toDto(userEntity);
        assertNull(userDto);
    }

    @Test
    public void Test3() {
        UserDto userDto = UserDto.builder().id("12345").name("name").themeId("1").backgroundId("2").menuId("3").build();
        UserEntity userEntity = userMapper.toEntity(userDto);
        assertEquals(userEntity.getId(), "12345");
        assertEquals(userEntity.getName(), "name");
        assertEquals(userEntity.getTheme().getId(), "1");
        assertEquals(userEntity.getBackground().getId(), "2");
        assertEquals(userEntity.getMenu().getId(), "3");
        assertNotNull(userEntity);
    }

    @Test
    public void Test4() {
        UserDto userDto = null;
        UserEntity userEntity = userMapper.toEntity(userDto);
        assertNull(userEntity);
    }

    @Test
    public void Test5() {
        ThemeEntity theme = new ThemeEntity();
        theme.setId("1");
        BackgroundEntity background = new BackgroundEntity();
        background.setId("2");
        MenuEntity menu = new MenuEntity();
        menu.setId("3");
        UserEntity userEntity = UserEntity.builder().id("12345").name("name").theme(theme).background(background).menu(menu).build();

        UserDto userDto = UserDto.builder().name("name1").themeId("10").backgroundId("20").menuId("30").build();
        userMapper.updateEntityFromDto(userDto, userEntity);

        assertEquals("12345", userEntity.getId());
        assertEquals(userEntity.getName(), "name1");
        assertEquals(userEntity.getTheme().getId(), "10");
        assertEquals(userEntity.getBackground().getId(), "20");
        assertEquals(userEntity.getMenu().getId(), "30");
        assertNotNull(userEntity);
    }

    @Test
    public void Test6() {
        ThemeEntity theme = new ThemeEntity();
        theme.setId("1");
        BackgroundEntity background = new BackgroundEntity();
        background.setId("2");
        MenuEntity menu = new MenuEntity();
        menu.setId("3");
        UserEntity userEntity = UserEntity.builder().id("12345").name("name").theme(theme).background(background).menu(menu).build();

        UserDto userDto = null;
        userMapper.updateEntityFromDto(userDto, userEntity);

        assertEquals("12345", userEntity.getId());
        assertEquals(userEntity.getName(), "name");
        assertEquals(userEntity.getTheme().getId(), "1");
        assertEquals(userEntity.getBackground().getId(), "2");
        assertEquals(userEntity.getMenu().getId(), "3");
        assertNotNull(userEntity);
    }

    @Test
    public void Test7() {
        UserEntity userEntity = UserEntity.builder().id("12345").name("name").theme(null).background(null).menu(null).build();

        UserDto userDto = UserDto.builder().name("name1").themeId("10").backgroundId("20").menuId("30").build();

        userMapper.updateEntityFromDto(userDto, userEntity);

        assertNotNull(userEntity.getTheme());
        assertEquals("10", userEntity.getTheme().getId());
        assertNotNull(userEntity.getBackground());
        assertEquals("20", userEntity.getBackground().getId());
        assertNotNull(userEntity.getMenu());
        assertEquals("30", userEntity.getMenu().getId());
        assertEquals("name1", userEntity.getName());
    }

    @Test
    public void Test8() {
        UserEntity userEntity = UserEntity.builder().id("12345").name("name").theme(null).background(null).menu(null).build();

        UserDto userDto = userMapper.toDto(userEntity);

        assertNotNull(userDto);
        assertNull(userDto.getThemeId());
        assertNull(userDto.getBackgroundId());
        assertNull(userDto.getMenuId());
    }

    @Test
    public void Test9() {
        ThemeEntity theme = new ThemeEntity();
        theme.setId(null);
        BackgroundEntity background = new BackgroundEntity();
        background.setId(null);
        MenuEntity menu = new MenuEntity();
        menu.setId(null);
        UserEntity userEntity = UserEntity.builder().id("12345").name("name").theme(theme).background(background).menu(menu).build();
        UserDto userDto = userMapper.toDto(userEntity);

        assertNotNull(userDto);
        assertNull(userDto.getThemeId());
        assertNull(userDto.getBackgroundId());
        assertNull(userDto.getMenuId());
    }

    @Test
    public void Test10() throws Exception {
        Method themeMethod = userMapper.getClass().getDeclaredMethod("userEntityThemeId", UserEntity.class);
        Method backgroundgMethod = userMapper.getClass().getDeclaredMethod("userEntityBackgroundId", UserEntity.class);
        Method menuMethod = userMapper.getClass().getDeclaredMethod("userEntityMenuId", UserEntity.class);

        themeMethod.setAccessible(true);
        backgroundgMethod.setAccessible(true);
        menuMethod.setAccessible(true);

        Object themeResult = themeMethod.invoke(userMapper, (UserEntity) null);
        Object backgroundResult = backgroundgMethod.invoke(userMapper, (UserEntity) null);
        Object menuResult = menuMethod.invoke(userMapper, (UserEntity) null);

        assertNull(themeResult);
        assertNull(backgroundResult);
        assertNull(menuResult);

        Method userDtoToThemeEntityMethod = userMapper.getClass().getDeclaredMethod("userDtoToThemeEntity", UserDto.class);
        Method userDtoToBackgroundEntityMethod = userMapper.getClass().getDeclaredMethod("userDtoToBackgroundEntity", UserDto.class);
        Method userDtoToMenuEntityMethod = userMapper.getClass().getDeclaredMethod("userDtoToMenuEntity", UserDto.class);

        Method userDtoToThemeEntity1Method = userMapper.getClass().getDeclaredMethod("userDtoToThemeEntity1", UserDto.class, ThemeEntity.class);
        Method userDtoToBackgroundEntity1Method = userMapper.getClass().getDeclaredMethod("userDtoToBackgroundEntity1", UserDto.class, BackgroundEntity.class);
        Method userDtoToMenuEntity1Method = userMapper.getClass().getDeclaredMethod("userDtoToMenuEntity1", UserDto.class, MenuEntity.class);

        userDtoToThemeEntityMethod.setAccessible(true);
        userDtoToBackgroundEntityMethod.setAccessible(true);
        userDtoToMenuEntityMethod.setAccessible(true);
        userDtoToThemeEntity1Method.setAccessible(true);
        userDtoToBackgroundEntity1Method.setAccessible(true);
        userDtoToMenuEntity1Method.setAccessible(true);

        assertNull(userDtoToThemeEntityMethod.invoke(userMapper, (UserDto) null));
        assertNull(userDtoToBackgroundEntityMethod.invoke(userMapper, (UserDto) null));
        assertNull(userDtoToMenuEntityMethod.invoke(userMapper, (UserDto) null));

        ThemeEntity themeObj = new ThemeEntity();
        userDtoToThemeEntity1Method.invoke(userMapper, (UserDto) null, themeObj);

        BackgroundEntity backgroundObj = new BackgroundEntity();
        userDtoToBackgroundEntity1Method.invoke(userMapper, (UserDto) null, backgroundObj);

        MenuEntity menuObj = new MenuEntity();
        userDtoToMenuEntity1Method.invoke(userMapper, (UserDto) null, menuObj);
    }
}
