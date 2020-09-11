package com.safereach.codechallenge.donottouch;

import com.safereach.codechallenge.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.safereach.codechallenge.donottouch.DataTestFactory.dataEntity;
import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class DataRepositoryTest {

    @Autowired
    private DataRepository dataRepository;

    @Test
    void shouldFindByContent() {
        //given
        String expectedContent = "someContent";
        Data expectedData = saveData(1L, expectedContent);
        saveData(2L, "OtherContent");
        saveData(3L, "someContentDifferent");
        //when
        List<Data> result = dataRepository.findByContent(expectedContent);
        //then
        assertThat(result).hasSize(1);
        Data data = result.get(0);
        assertThat(data.getId()).isEqualTo(expectedData.getId());
        assertThat(data.getContent()).isEqualTo(expectedContent);
    }

    @Test
    void shouldNotByContent() {
        //given
        String content = "someContent";
        saveData(1L, "OtherContent");
        //when
        List<Data> data = dataRepository.findByContent(content);
        //then
        assertThat(data).isEmpty();
    }

    private Data saveData(Long id, String content) {
        return dataRepository.save(dataEntity(id, content));
    }

}
