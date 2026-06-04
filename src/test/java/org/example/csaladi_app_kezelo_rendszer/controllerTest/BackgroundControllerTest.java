package org.example.csaladi_app_kezelo_rendszer.controllerTest;

import org.example.csaladi_app_kezelo_rendszer.config.SecurityConfig;
import org.example.csaladi_app_kezelo_rendszer.controller.BackgroundController;
import org.example.csaladi_app_kezelo_rendszer.service.BackgroundService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(BackgroundController.class)
@Import(SecurityConfig.class)
public class BackgroundControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BackgroundService backgroundService;

    @Test
    public void Test1() {

    }
}
