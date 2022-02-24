package ai.ecma.academic.controller;

import ai.ecma.academic.payload.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.junit.jupiter.api.Assertions.*;

class CourseControllerTest {

    @Autowired
    CourseController courseController;

    @Test
    void bla2() throws Exception{
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Hayot");

        assertEquals("Hayot salom", courseController.bla2(userDTO));

    }
}