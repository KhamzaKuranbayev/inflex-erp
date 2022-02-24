package ai.ecma.academic.controller;

import ai.ecma.academic.entity.Ketmon;
import ai.ecma.academic.entity.KetmonRepository;
import ai.ecma.academic.service.KetmonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class KetmonControllerTest {

    @Autowired
    private KetmonService ketmonService;

    @MockBean
    private KetmonRepository ketmonRepository;

    @Test
    void addKetmon() {
    }

    @Test
    void getKetmons() {
    }

    @Test
    void editKetmon() {
    }

    @Test
    void getKetmonById() {
        Optional<Ketmon> optionalKetmon=Optional.of(new Ketmon("Ketmon1"));
        Optional<Ketmon> ketmon = ketmonRepository.findById(optionalKetmon.get().getId());
        if (ketmon.isEmpty()){
            System.out.println("Failed getKetmonById");
        }
        else
            System.out.println("Success getKetmonById");
    }

    @Test
    void deleteKetmon() {
    }
}

