package org.inbank.petko.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.inbank.petko.TestFactory;
import org.inbank.petko.dto.CreditOrderDto;
import org.inbank.petko.dto.DecisionDto;
import org.inbank.petko.entity.UserEntity;
import org.inbank.petko.service.CreditDecisionService;
import org.inbank.petko.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CreditController.class)
public class CreditControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    UserService userService;
    @MockBean
    CreditDecisionService decisionService;

    @Test
    void getStartPageShouldReturnUsers() throws Exception {
        mockMvc.perform(get("/credit")).andExpect(status().isOk())
                .andExpect(model().attributeExists("users"));
    }

    @Test
    void performCreditDecisionJsonShouldReturnDecisionDto() throws Exception {
        UserEntity userEntity = TestFactory.createUser(49002010976L);
        CreditOrderDto creditOrder = TestFactory.populateCreditOrder(49002010976L, 7000, 37, userEntity);
        DecisionDto decisionDto = new DecisionDto(DecisionDto.DecisionStatus.POSITIVE, 7000, 37, Locale.getDefault());

        when(decisionService.performCreditDecision(any(CreditOrderDto.class))).thenReturn(decisionDto);
        mockMvc.perform(post("/credit")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(creditOrder)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(decisionDto)));
    }

    @Test
    void performCreditDecisionHtmlShouldReturnModelAndView() throws Exception {
        UserEntity userEntity = TestFactory.createUser(49002010976L);
        CreditOrderDto creditOrder = TestFactory.populateCreditOrder(49002010976L, 7000, 37, userEntity);
        DecisionDto decisionDto = new DecisionDto(DecisionDto.DecisionStatus.POSITIVE, 7000, 37, Locale.getDefault());

        when(decisionService.performCreditDecision(any(CreditOrderDto.class))).thenReturn(decisionDto);
        mockMvc.perform(post("/credit").flashAttr("creditOrder", creditOrder))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("decision"));
    }

    @Test
    void performCreditDecisionJsonWithInvalidAttrsShouldReturn4xxError() throws Exception {
        UserEntity userEntity = TestFactory.createUser(49002010976L);
        CreditOrderDto creditOrder = TestFactory.populateCreditOrder(49002010976L, -1000, -37, userEntity);
        DecisionDto decisionDto = new DecisionDto(DecisionDto.DecisionStatus.POSITIVE, -1000, -37, Locale.getDefault());

        when(decisionService.performCreditDecision(any(CreditOrderDto.class))).thenReturn(decisionDto);
        mockMvc.perform(post("/credit")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(creditOrder)))
                .andExpect(status().is4xxClientError());
    }
}
