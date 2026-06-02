package org.example.csaladi_app_kezelo_rendszer.mapperTest;

import org.example.csaladi_app_kezelo_rendszer.dto.IconDto;
import org.example.csaladi_app_kezelo_rendszer.entity.ApplicationEntity;
import org.example.csaladi_app_kezelo_rendszer.entity.IconEntity;
import org.example.csaladi_app_kezelo_rendszer.entity.MenuEntity;
import org.example.csaladi_app_kezelo_rendszer.mapper.IconMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class IconMapperTest {

    @Autowired
    private IconMapper iconMapper;

    @Test
    public void Test1() {
        IconEntity iconEntity = null;
        IconDto iconDto = iconMapper.toDto(iconEntity);
        assertNull(iconDto);
    }

    @Test
    public void Test2() {
        MenuEntity menu = new MenuEntity();
        menu.setId("1");
        ApplicationEntity app = new ApplicationEntity();
        app.setId("2");
        IconEntity iconEntity = IconEntity.builder().id("12345").name("name").menu(menu).application(app).build();
        IconDto iconDto = iconMapper.toDto(iconEntity);
        assertNotNull(iconDto);
        assertEquals("12345", iconDto.getId());
        assertEquals("name", iconDto.getName());
        assertEquals("1", iconDto.getMenuId());
        assertEquals("2", iconDto.getApplicationId());
    }

    @Test
    public void Test3() {
        IconDto iconDto = null;
        IconEntity iconEntity = iconMapper.toEntity(iconDto);
        assertNull(iconEntity);
    }

    @Test
    public void Test4() {
        IconDto iconDto = IconDto.builder().id("12345").name("name").menuId("1").applicationId("2").build();
        IconEntity iconEntity = iconMapper.toEntity(iconDto);
        assertNotNull(iconEntity);
        assertEquals("12345", iconEntity.getId());
        assertEquals("name", iconEntity.getName());
        assertEquals("1", iconEntity.getMenu().getId());
        assertEquals("2", iconEntity.getApplication().getId());
    }

    @Test
    public void Test5() {
        IconDto iconDto = null;
        IconEntity iconEntity = IconEntity.builder().id("12345").name("name").build();
        iconMapper.updateEntityFromDto(iconDto, iconEntity);
        assertEquals("name", iconEntity.getName());
        assertEquals("12345", iconEntity.getId());
    }

    @Test
    public void Test6() {
        MenuEntity menu = new MenuEntity();
        menu.setId("1");
        ApplicationEntity app = new ApplicationEntity();
        app.setId("2");
        IconEntity iconEntity = IconEntity.builder().id("12345").name("name").menu(menu).application(app).build();

        IconDto iconDto = IconDto.builder().name("name1").menuId("10").applicationId("20").build();
        iconMapper.updateEntityFromDto(iconDto, iconEntity);

        assertEquals("12345", iconEntity.getId());
        assertEquals("name1", iconEntity.getName());
        assertEquals("10", iconEntity.getMenu().getId());
        assertEquals("20", iconEntity.getApplication().getId());
    }

    @Test
    public void Test7() {
        IconEntity iconEntity = IconEntity.builder().id("12345").name("name").menu(null).application(null).build();
        IconDto iconDto = iconMapper.toDto(iconEntity);
        assertNotNull(iconDto);
        assertNull(iconDto.getMenuId());
        assertNull(iconDto.getApplicationId());
    }

    @Test
    public void Test8() {
        MenuEntity menu = new MenuEntity();
        menu.setId(null);
        ApplicationEntity app = new ApplicationEntity();
        app.setId(null);
        IconEntity iconEntity = IconEntity.builder().id("12345").name("name").menu(menu).application(app).build();
        IconDto iconDto = iconMapper.toDto(iconEntity);
        assertNotNull(iconDto);
        assertNull(iconDto.getMenuId());
        assertNull(iconDto.getApplicationId());
    }

    @Test
    public void Test9() {
        IconEntity iconEntity = IconEntity.builder().id("12345").name("name").menu(null).application(null).build();
        IconDto iconDto = IconDto.builder().name("name1").menuId("10").applicationId("20").build();
        iconMapper.updateEntityFromDto(iconDto, iconEntity);

        assertNotNull(iconEntity.getMenu());
        assertEquals("10", iconEntity.getMenu().getId());
        assertNotNull(iconEntity.getApplication());
        assertEquals("20", iconEntity.getApplication().getId());
    }

    @Test
    public void Test10() throws Exception {
        Class<?> implClass = iconMapper.getClass();

        Method menuMethod = implClass.getDeclaredMethod("iconEntityMenuId", IconEntity.class);
        Method appMethod = implClass.getDeclaredMethod("iconEntityApplicationId", IconEntity.class);

        menuMethod.setAccessible(true);
        appMethod.setAccessible(true);

        assertNull(menuMethod.invoke(iconMapper, (IconEntity) null));
        assertNull(appMethod.invoke(iconMapper, (IconEntity) null));

        Method iconDtoToMenuEntityMethod = implClass.getDeclaredMethod("iconDtoToMenuEntity", IconDto.class);
        Method iconDtoToApplicationEntityMethod = implClass.getDeclaredMethod("iconDtoToApplicationEntity", IconDto.class);

        Method iconDtoToMenuEntity1Method = implClass.getDeclaredMethod("iconDtoToMenuEntity1", IconDto.class, MenuEntity.class);
        Method iconDtoToApplicationEntity1Method = implClass.getDeclaredMethod("iconDtoToApplicationEntity1", IconDto.class, ApplicationEntity.class);

        iconDtoToMenuEntityMethod.setAccessible(true);
        iconDtoToApplicationEntityMethod.setAccessible(true);
        iconDtoToMenuEntity1Method.setAccessible(true);
        iconDtoToApplicationEntity1Method.setAccessible(true);

        assertNull(iconDtoToMenuEntityMethod.invoke(iconMapper, (IconDto) null));
        assertNull(iconDtoToApplicationEntityMethod.invoke(iconMapper, (IconDto) null));

        MenuEntity menuObj = new MenuEntity();
        iconDtoToMenuEntity1Method.invoke(iconMapper, (IconDto) null, menuObj);

        ApplicationEntity appObj = new ApplicationEntity();
        iconDtoToApplicationEntity1Method.invoke(iconMapper, (IconDto) null, appObj);
    }
}
