/* Created by IntelliJ IDEA.
 User: Mirzaahmatov Aziz
Date: 5/21/2021
Time: 3:57 PM
 To change this template use File | Settings | File Templates.
*/
package ai.ecma.appaccount.utils;

//import ai.ecma.academic.exceptions.BadRequestException;
//import ai.ecma.academic.exceptions.ForbiddenException;
//import ai.ecma.academic.exceptions.ServerErrorException;
//import ai.ecma.academic.exceptions.UnauthorizedException;

//import ai.ecma.appaccount.payload.ApiResponse;
import ai.ecma.appaccount.payload.UserDTO;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class CommonUtils {

    //MUVAFFAQIAYTLI AMAL BAJARILGACH XABAR QAYTARISH UCHUN
//    public static ApiResponse successResponse(String message) {
//        return new ApiResponse(message);
//    }


//    public static ServerErrorException SERVER_ERROR_EXCEPTION(Exception e, String message) {
//        e.printStackTrace();
//        return new ServerErrorException(message);
//    }
//
//    public static ForbiddenException FORBIDDEN_EXCEPTION(String message) {
//        return new ForbiddenException(message);
//    }
//
//    public static UnauthorizedException UNAUTHORIZED_EXCEPTION(String message) {
//        return new UnauthorizedException(message);
//    }

//    public static BadRequestException BAD_REQUEST_EXCEPTION(String message) {
//        return new BadRequestException(message);
//    }


    public static HttpServletRequest currentRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return Optional.ofNullable(servletRequestAttributes).map(ServletRequestAttributes::getRequest).orElse(null);
    }

    public static UserDTO getUserDTOFromRequest() {
        try {
            HttpServletRequest httpServletRequest = currentRequest();
            return (UserDTO) httpServletRequest.getAttribute(RestConstants.REQUEST_ATTRIBUTE_CURRENT_USER);
        } catch (Exception e) {
            return null;
        }
    }
}
