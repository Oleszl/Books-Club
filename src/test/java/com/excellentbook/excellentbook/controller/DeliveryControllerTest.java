package com.excellentbook.excellentbook.controller;

import com.excellentbook.excellentbook.service.CategoryService;
import com.excellentbook.excellentbook.service.DeliveryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class DeliveryControllerTest {
    private static final String deliveryLink = "/api/v1/delivery";
    private MockMvc mockMvc;

    @InjectMocks
    private DeliveryController deliveryController;

    @Mock
    private DeliveryService deliveryService;
    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(deliveryController)
                .build();
    }
    @Test
    void getAllAvailablePostOfficesTest() throws Exception {
        String pageNumber = "0";
        String pageSize = "25";
        mockMvc.perform(get(deliveryLink)
                .queryParam("city", "Львів"))
                .andExpect(status().isOk());
        verify(deliveryService).getAllAvailablePostOffices("Львів",pageNumber,pageSize);
    }
}
