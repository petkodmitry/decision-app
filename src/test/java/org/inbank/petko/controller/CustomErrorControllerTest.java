package org.inbank.petko.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.inbank.petko.dto.ErrorDto;
import org.inbank.petko.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomErrorController.class)
public class CustomErrorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    UserService userService;
    @Mock
    HttpServletRequest request;

    @Test
    void handleErrorJsonShouldReturnErrorDto() throws Exception {
        ErrorDto errorDto = new ErrorDto("Test error");
        mockMvc.perform(post("/error", request)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(this::getPostProcessRequest)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(errorDto)))
        ;
    }

    @Test
    void handleErrorHtmlShouldReturnModelAndView() throws Exception {
        ErrorDto errorDto = new ErrorDto("Test error");
        mockMvc.perform(post("/error", request).with(this::getPostProcessRequest))
                .andExpect(status().is4xxClientError())
                .andExpect(model().attribute("decision", errorDto));
    }

    private MockHttpServletRequest getPostProcessRequest(MockHttpServletRequest request) {
        request.setAttribute(RequestDispatcher.ERROR_EXCEPTION, new RuntimeException("Test error"));
        return request;
    }
}
