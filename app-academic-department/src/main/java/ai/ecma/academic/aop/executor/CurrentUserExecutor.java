package ai.ecma.academic.aop.executor;

import ai.ecma.academic.aop.annotation.CurrentUser;
import ai.ecma.academic.payload.UserDTO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Arrays;

import static ai.ecma.academic.utils.CommonUtils.getUserDTOFromRequest;

@Aspect
@Component
public class CurrentUserExecutor {


    @Before("execution(* ai.ecma.academic.controller.*.*(..))")
    public void paramCheck(JoinPoint joinPoint) throws Exception {
        // Getting parameter objects
        Object[] args = joinPoint.getArgs();
        // Obtaining method parameters
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Parameter[] parameters = signature.getMethod().getParameters();
        for (Parameter parameter : parameters) {
            //IF ANNOTATION IS NOT MY METHOD WILL BE RETURN HERE
            CurrentUser currentUser = parameter.getAnnotation(CurrentUser.class);
            if (currentUser == null)
                return;
            // How Java handles its own basic types of parameters (such as Integer, String)
            if (isPrimite(parameter.getType())) {
                continue;
            }
            Class<?> paramClazz = parameter.getType();

            // Get the parameter object corresponding to the type, and the interface in Controller in the actual project will not pass two parameters of the same custom type, so findFirst () is used directly here.
            Object arg = Arrays.stream(args).filter(ar -> paramClazz.isAssignableFrom(ar.getClass())).findFirst().orElseThrow();
            // All member variables that get the parameters
            Field[] declaredFields = paramClazz.getDeclaredFields();
            UserDTO userDTO = getUserDTOFromRequest();
            if (userDTO == null)
                return;
            for (Field field : declaredFields) {
                field.setAccessible(true);
                field.set(arg, field.get(userDTO));
            }
        }
    }

    /**
     * Determine whether it is a basic type: including String
     *
     * @param clazz clazz
     * @ return true: yes; false: no
     */
    private boolean isPrimite(Class<?> clazz) {
        return clazz.isPrimitive() || clazz == String.class;
    }

}
