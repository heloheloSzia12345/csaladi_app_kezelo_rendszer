package org.example.csaladi_app_kezelo_rendszer.controllerTest;

import org.example.csaladi_app_kezelo_rendszer.config.SecurityConfig;
import org.example.csaladi_app_kezelo_rendszer.controller.ApplicationController;
import org.example.csaladi_app_kezelo_rendszer.service.ApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(ApplicationController.class)
@Import(SecurityConfig.class)
public class ApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ApplicationService applicationService;

    @Test
    public void Test1() {

    }
}
