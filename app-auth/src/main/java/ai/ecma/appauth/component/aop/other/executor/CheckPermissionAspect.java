package ai.ecma.appauth.component.aop.other.executor;

import ai.ecma.appauth.service.MainService;
import ai.ecma.appauth.component.aop.other.annotation.CheckPermission;
import ai.ecma.appauth.entity.User;
import ai.ecma.appauth.exceptions.RestException;
import ai.ecma.appauth.service.UserServiceImpl;
import ai.ecma.appauth.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class CheckPermissionAspect {

    private final UserServiceImpl userServiceImpl;
    private final MainService mainService;

    @Before(value = "@annotation(checkPermission)")
    public void checkPermissionExecutor(CheckPermission checkPermission) {
        User user = CommonUtils.getUserFromSecurityContext();
        if (user == null)
            throw RestException.restThrow(mainService.getMessageByKey("USER_NOT_FOUND"), HttpStatus.NOT_FOUND);
        userServiceImpl.checkPermission(user.getId(),
                Arrays.asList(checkPermission.permissions()));
    }

}