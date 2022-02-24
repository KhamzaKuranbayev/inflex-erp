package ai.ecma.appaccount.controller;

import ai.ecma.appaccount.aop.annotation.CheckAuth;
import ai.ecma.appaccount.aop.annotation.CurrentUser;
import ai.ecma.appaccount.entity.enums.PermissionEnum;
import ai.ecma.appaccount.exceptions.RestException;
import ai.ecma.appaccount.payload.CurrencyDto;
import ai.ecma.appaccount.payload.UserDTO;
import ai.ecma.appaccount.service.CurrencyService;
import ai.ecma.appaccount.utils.RestConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static ai.ecma.appaccount.entity.enums.PermissionEnum.ADD_CURRENCY;

@RestController
@RequestMapping(value = RestConstants.BASE_PATH + "currency")
public class CurrencyController {
    @Autowired
    CurrencyService currencyService;

    @CheckAuth
    @PostMapping
    public HttpEntity<?> addCourse() {
        System.out.println("qalay");
        return null;
    }

//    @CheckAuth(permission = {ADD_CURRENCY})
//    @GetMapping
//    public String bla2(@CurrentUser UserDTO userDTO) {
//        return userDTO.getFirstName() + " salom";
//    }

    @GetMapping
    public String bla2() {
        return " salom ddddd";
    }

    @CheckAuth
    @PutMapping
    public void bla(CurrencyDto currencyDto) {
        throw new RestException("Qalay", HttpStatus.BAD_REQUEST);
    }



}
