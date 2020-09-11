package com.safereach.codechallenge;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safereach.codechallenge.donottouch.Data;
import com.safereach.codechallenge.donottouch.DoNotTouchProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.safereach.codechallenge.donottouch.DataTestFactory.dataEntity;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class RunControllerTest {

    @Mock
    private DoNotTouchProcessor processor;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new RunController(processor)).build();
    }

    @Test
    void shouldRunProcessor() throws Exception {
        //given
        Data expectedData = dataEntity();
        when(processor.run()).thenReturn(asList(expectedData));
        //when
        ResultActions result = mockMvc.perform(get("/run")
                .accept(APPLICATION_JSON_VALUE));
        //then
        result.andExpect(status().isOk());
        List<Data> data = fromJson(result.andReturn().getResponse().getContentAsString());
        assertThat(data).hasSize(1);
        assertThat(data.get(0).getId()).isEqualTo(expectedData.getId());
        assertThat(data.get(0).getContent()).isEqualTo(expectedData.getContent());
    }

    private List<Data> fromJson(String content) throws JsonProcessingException {
        return new ObjectMapper().readValue(content, new TypeReference<List<Data>>() {
        });
    }

}
