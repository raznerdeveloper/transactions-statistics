package com.n26.controller;

import com.n26.model.Statistics;
import com.n26.model.Transaction;
import com.n26.model.TransactionStore;
import com.n26.service.TransactionService;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatisticsController.class)
public class StatisticsControllerTest extends AbstractControllerTest{

    private static final String CONTROLLER_PATH = "/statistics";

    @MockBean
    private TransactionService transactionService;

    @Test
    public void getStatisticsForSingleTransaction() throws Exception {

        TransactionStore transactionStore = new TransactionStore();
        transactionStore.addTransaction(new Transaction(156.126, new Date()));
        Statistics statistics = new Statistics(transactionStore);

        when(transactionService.getStatistics()).thenReturn(statistics);

        this.mockMvc.perform(get(CONTROLLER_PATH).accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sum", is("156.13")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.avg", is("156.13")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.max", is("156.13")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.min", is("156.13")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.count", is(1)));
    }

    @Test
    public void getStatisticsForNoTransaction() throws Exception {

        TransactionStore transactionStore = new TransactionStore();
        Statistics statistics = new Statistics(transactionStore);

        when(transactionService.getStatistics()).thenReturn(statistics);

        this.mockMvc.perform(get(CONTROLLER_PATH).accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sum", is("0.00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.avg", is("0.00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.max", is("0.00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.min", is("0.00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.count", is(0)));
    }
}