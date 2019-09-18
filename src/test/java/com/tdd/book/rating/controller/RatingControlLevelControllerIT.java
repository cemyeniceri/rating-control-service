package com.tdd.book.rating.controller;


import com.tdd.book.rating.exceptions.BookNotFoundException;
import com.tdd.book.rating.service.RatingControlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class RatingControlLevelControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingControlService ratingControlService;

    @Test
    public void shouldReturnNotFound_whenCustomerControlLevelAndBookIdIsNotProvided() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rcl/book/v1/read/eligibility")
                .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void shouldReturnBadRequest_whenInvalidCustomerControlLevelAndBookIdIsProvided() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rcl/book/v1/read/eligibility/CONTROLXXX*/IDXXX*")
                .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldReturnTrue_whenBookServiceControlLevelIsEqualTo_CustomerRatingControleLevel_ForRequestedBookId() throws Exception {
        given(ratingControlService.canReadBook(anyString(), anyString())).willReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.get("/rcl/book/v1/read/eligibility/12/B1234")
                .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
        verify(ratingControlService).canReadBook(anyString(),anyString());
    }

    @Test
    public void shouldReturnNotFound_whenBookNotFoundExceptionIsThrown() throws Exception {
        given(ratingControlService.canReadBook(anyString(), anyString())).willThrow(new BookNotFoundException("Book not Found"));
        mockMvc.perform(MockMvcRequestBuilders.get("/rcl/book/v1/read/eligibility/12/B1234")
                .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("{\"code\":\"404\",\"message\":\"Book not Found\"}"));
    }
}
