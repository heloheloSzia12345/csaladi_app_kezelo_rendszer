package org.example.csaladi_app_kezelo_rendszer.controllerTest;

import org.example.csaladi_app_kezelo_rendszer.config.SecurityConfig;
import org.example.csaladi_app_kezelo_rendszer.controller.UserController;
import org.example.csaladi_app_kezelo_rendszer.dto.UserDto;
import org.example.csaladi_app_kezelo_rendszer.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Test
    public void Test1() throws Exception {
        UserDto userDto1 = UserDto.builder().id("1").name("10").build();
        UserDto userDto2 = UserDto.builder().id("2").name("20").build();
        when(userService.getAllUser()).thenReturn(List.of(userDto1, userDto2));

        mockMvc.perform(get("/manager/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("10"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].name").value("20"));

        verify(userService, times(1)).getAllUser();
    }

    @Test
    public void Test2() throws Exception {
        String id = "1";
        UserDto userDto = UserDto.builder().id("1").name("10").build();

        when(userService.getUserById(id)).thenReturn(userDto);

        mockMvc.perform(get("/manager/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("10"));
        verify(userService, times(1)).getUserById(id);
    }

    @Test
    public void Test3() throws Exception {
        
    }
}
