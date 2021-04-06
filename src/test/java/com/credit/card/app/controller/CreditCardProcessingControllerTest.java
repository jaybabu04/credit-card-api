package com.credit.card.app.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.credit.card.app.model.CardInfo;
import com.credit.card.app.service.CreditCardService;

import java.util.ArrayList;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {CreditCardProcessingController.class})
@ExtendWith(SpringExtension.class)
public class CreditCardProcessingControllerTest {
    @Autowired
    private CreditCardProcessingController creditCardProcessingController;

    @MockBean
    private CreditCardService creditCardService;

    @Test
    public void testGetAllCardInfo() throws Exception {
        when(this.creditCardService.getAllCardInfo()).thenReturn(new ArrayList<CardInfo>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/credit-card");
        MockMvcBuilders.standaloneSetup(this.creditCardProcessingController)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("[]")));
    }

    @Test
    public void save() throws Exception {
        when(this.creditCardService.save(any())).thenReturn(new CardInfo());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/credit-card")
                .content("{ \"name\":\"Ganesh Siva\", \"cardNumber\":\"79927398713\", \"creditlimit\":1000.0, \"balance\":0.0 }")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MockMvcBuilders.standaloneSetup(this.creditCardProcessingController)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isCreated());
    }

}

