/* Created by IntelliJ IDEA.
 User: Mirzaahmatov Aziz
Date: 5/21/2021
Time: 3:57 PM
 To change this template use File | Settings | File Templates.
*/
package ai.ecma.academic.utils;

import ai.ecma.academic.payload.ApiResponse;
import ai.ecma.academic.payload.UserDTO;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ServerErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class CommonUtils {

    //MUVAFFAQIAYTLI AMAL BAJARILGACH XABAR QAYTARISH UCHUN
    public static ApiResponse successResponse(String message) {
        return new ApiResponse(message);
    }


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
