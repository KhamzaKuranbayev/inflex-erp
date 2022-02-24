package ai.ecma.academic.aop.executor;

import ai.ecma.academic.aop.annotation.CheckAuth;
import ai.ecma.academic.exceptions.RestException;
import ai.ecma.academic.feign.AuthFeign;
import ai.ecma.academic.payload.UserDTO;
import ai.ecma.academic.utils.RestConstants;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.stream.Collectors;

import static ai.ecma.academic.utils.CommonUtils.currentRequest;

@Order(value = 1)
@Aspect
@Component
public class CheckAuthAspect {

    @Autowired
    AuthFeign authFeign;


    @Before(value = "@annotation(checkAuth)")
    public void checkAuthExecutor(CheckAuth checkAuth) {
        check(checkAuth);
    }


    private String getTokenFromRequest() {
        try {
            return currentRequest().getHeader("Authorization");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    public void check(CheckAuth checkAuth) {
        String token = getTokenFromRequest();
        if (token == null) {
            throw new RestException("Kirish mumkin emas", HttpStatus.UNAUTHORIZED);
        }
        UserDTO user;
        if (checkAuth.permission().length == 0) {
            user = authFeign.checkUser(token);
        } else {
            user = authFeign.checkPermission(token, Arrays.stream(checkAuth.permission()).map(Enum::name).collect(Collectors.toSet()));
        }
        HttpServletRequest httpServletRequest = currentRequest();
        httpServletRequest.setAttribute(RestConstants.REQUEST_ATTRIBUTE_CURRENT_USER, user);
    }

}
