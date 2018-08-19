package com.n26.controller;

import com.n26.exception.StaleTransactionException;
import com.n26.service.TransactionService;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest extends AbstractControllerTest{

    private static final String CONTROLLER_PATH = "/transactions";

    @MockBean
    private TransactionService transactionService;

    @Test
    public void createValidTransaction() throws Exception {
        String validTransaction = String.format(
                "{\"amount\":\"123.456\", \"timestamp\": \"%s\"}",
                Instant.now().toString()
        );

        this.mockMvc.perform(post(CONTROLLER_PATH)
                .content(validTransaction)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isCreated())
                .andExpect(content().string(""));
    }

    @Test
    public void createStaleTransaction() throws Exception {
        doThrow(StaleTransactionException.class).when(transactionService).addTransaction(any());

        String staleTransaction = String.format(
                "{\"amount\":\"123.456\", \"timestamp\": \"%s\"}",
                Instant.now().minusSeconds(120).toString()
        );

        this.mockMvc.perform(post(CONTROLLER_PATH)
                .content(staleTransaction)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void createTransactionInvalidJson() throws Exception {
        String staleTransaction = "{\"amount\":\"123.456\"";

        this.mockMvc.perform(post(CONTROLLER_PATH)
                .content(staleTransaction)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    public void createTransactionInvalidFormat() throws Exception {
        String staleTransaction = "{\"amount\":\"123.456\", \"timestamp\": \"4/23/2018 11:32 PM\"}";

        this.mockMvc.perform(post(CONTROLLER_PATH)
                .content(staleTransaction)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(""));
    }

    @Test
    public void deleteTransactions() throws Exception {
        this.mockMvc.perform(delete(CONTROLLER_PATH))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

}