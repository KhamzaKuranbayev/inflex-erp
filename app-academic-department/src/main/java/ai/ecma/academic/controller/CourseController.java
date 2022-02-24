package ai.ecma.academic.controller;

import ai.ecma.academic.aop.annotation.CheckAuth;
import ai.ecma.academic.aop.annotation.CurrentUser;
import ai.ecma.academic.entity.Ketmon;
import ai.ecma.academic.entity.KetmonRepository;
import ai.ecma.academic.exceptions.RestException;
import ai.ecma.academic.payload.UserDTO;
import ai.ecma.academic.utils.RestConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static ai.ecma.academic.entity.enums.PermissionEnum.ADD_COURSE;

@RestController
@RequestMapping(value = RestConstants.BASE_PATH + "course")
public class CourseController {

    @Autowired
    KetmonRepository ketmonRepository;

    //    @CheckAuth
    @PostMapping
    public HttpEntity<?> addCourse() {
        System.out.println("qalay");
        return null;
    }

    @CheckAuth(permission = {ADD_COURSE})
    @GetMapping
    public String bla2(@CurrentUser UserDTO userDTO) {
        Ketmon ketmon = ketmonRepository.save(new Ketmon(userDTO.getFirstName()));
        return ketmon.getName() + " salom";
    }

    @CheckAuth
    @PutMapping
    public void bla(Ketmon ketmon) {
        throw new RestException("Qalay", HttpStatus.BAD_REQUEST);
    }


}
