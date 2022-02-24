package ai.ecma.appauth.component.aop.other.annotation;

import ai.ecma.appauth.entity.enums.AuthPermissionEnum;

import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface CheckPermission {

    AuthPermissionEnum[] permissions();


}
