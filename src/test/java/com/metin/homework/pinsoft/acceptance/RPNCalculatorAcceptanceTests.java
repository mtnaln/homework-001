package com.metin.homework.pinsoft.acceptance;

import com.metin.homework.pinsoft.HomeworkApplication;
import com.metin.homework.pinsoft.exception.InvalidInputException;
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
class RPNCalculatorAcceptanceTests {

    @Autowired
    private MockMvc mvc;

    @Test
    void onlyOneOperation() throws Exception {
        final double EXCEPTED_VALUE = 4;

        mvc.perform(get("/api/calculation?expression=20 5 /").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(String.valueOf(EXCEPTED_VALUE)));
    }

    @Test
    void multipleOperation() throws Exception {
        final double EXCEPTED_VALUE = 141;

        mvc.perform(get("/api/calculation?expression=3 5 8 * 7 + *").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(String.valueOf(EXCEPTED_VALUE)));
    }

    @Test
    void invalidSpecialCharacter() {
        final String EXCEPTED_EXCEPTION_MESSAGE = "Invalid Input";

        assertThatThrownBy(() -> mvc.perform(get("/api/calculation?expression=20 5 %")
                .accept(MediaType.APPLICATION_JSON)))
                .hasCauseInstanceOf(InvalidInputException.class)
                .hasMessageContaining(EXCEPTED_EXCEPTION_MESSAGE);
    }

    @Test
    void invalidNumberFormat() {
        final String EXCEPTED_EXCEPTION_MESSAGE = "Invalid Input";

        assertThatThrownBy(() -> mvc.perform(get("/api/calculation?expression=2u 5 /")
                .accept(MediaType.APPLICATION_JSON)))
                .hasCauseInstanceOf(InvalidInputException.class)
                .hasMessageContaining(EXCEPTED_EXCEPTION_MESSAGE);
    }
}
