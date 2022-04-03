package com.metin.homework.pinsoft.acceptance;

import com.metin.homework.pinsoft.HomeworkApplication;
import com.metin.homework.pinsoft.exception.InconsistentColumnNumberException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = HomeworkApplication.class)
class MineSweeperAcceptanceTests {

    @Autowired
    private MockMvc mvc;

    @Test
    void singleDimension() throws Exception {
        final String EXCEPTED_VALUE = "[\"**11*\"]";

        mvc.perform(get("/api/mine-sweeper?expression=**..*").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(EXCEPTED_VALUE));
    }

    @Test
    void multiDimensions() throws Exception {
        final String EXCEPTED_VALUE = "[\"**100\",\"33200\",\"1*100\"]";

        mvc.perform(get("/api/mine-sweeper?expression=**...,.....,.*...").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(EXCEPTED_VALUE));
    }

    @Test
    void inconsistentColumnNumber() throws Exception {
        final String EXCEPTED_EXCEPTION_MESSAGE = "Inconsistent Column Number";

        assertThatThrownBy(() -> mvc.perform(get("/api/mine-sweeper?expression=**...,...,.*..")
                .accept(MediaType.APPLICATION_JSON)))
                .hasCauseInstanceOf(InconsistentColumnNumberException.class)
                .hasMessageContaining(EXCEPTED_EXCEPTION_MESSAGE);
    }
}
