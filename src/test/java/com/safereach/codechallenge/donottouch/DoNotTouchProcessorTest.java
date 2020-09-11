package com.safereach.codechallenge.donottouch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.safereach.codechallenge.donottouch.DataTestFactory.dataEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class DoNotTouchProcessorTest {

    @Mock
    private DataRepository dataRepository;

    private DoNotTouchProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new DoNotTouchProcessor(dataRepository);
    }

    @Test
    void shouldFindData() {
        //given
        Data expectedData = dataEntity();
        when(dataRepository.findByContent(anyString())).thenReturn(List.of(expectedData));
        //when
        List<Data> data = processor.run();
        //then
        assertThat(data).containsOnly(expectedData);
    }

    @Test
    void shouldNotFindData() {
        //given
        when(dataRepository.findByContent(anyString())).thenReturn(null);
        //when
        List<Data> data = processor.run();
        //then
        assertThat(data).isEmpty();
    }

}
