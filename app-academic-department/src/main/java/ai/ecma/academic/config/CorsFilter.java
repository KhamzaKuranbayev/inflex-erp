/* Created by IntelliJ IDEA.
 User: Mirzaahmatov Aziz
Date: 5/24/2021
Time: 9:36 AM
 To change this template use File | Settings | File Templates.
*/
package ai.ecma.academic.config;

import ai.ecma.academic.payload.ApiResult;
import ai.ecma.academic.payload.ErrorData;
import ai.ecma.academic.utils.RestConstants;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CorsFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (RestConstants.corsOrigins.contains(request.getRemoteAddr())) {
            filterChain.doFilter(request, response);
        } else {
            Gson gson = new Gson();
            ApiResult<ErrorData> errorDataApiResult = new ApiResult<>("Kirish mumkin emas", 403);
            String s = gson.toJson(errorDataApiResult);
            response.getWriter().write(s);
            response.setStatus(403);
            response.setContentType("application/json");
        }


    }


}
