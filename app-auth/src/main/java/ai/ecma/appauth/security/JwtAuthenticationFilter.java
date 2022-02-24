package ai.ecma.appauth.security;

import ai.ecma.appauth.entity.User;
import ai.ecma.appauth.payload.ApiResult;
import ai.ecma.appauth.payload.ErrorData;
import ai.ecma.appauth.repository.UserRepository;
import ai.ecma.appauth.service.MainService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Gson gson;
    private final MainService mainService;

    @Value("${auth.serviceUsername}")
    private String myServiceUsername;
    @Value("${auth.servicePassword}")
    private String myServicePassword;


    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest httpServletRequest,
                                    @NotNull HttpServletResponse httpServletResponse,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        if (!httpServletRequest.getRequestURI().equals("/v2/api-docs")) {
            String serviceName = httpServletRequest.getHeader("serviceName");
            String servicePassword = httpServletRequest.getHeader("servicePassword");
            if (serviceName == null
                    || servicePassword == null
                    || !serviceName.equals(myServiceUsername)
                    || !servicePassword.equals(myServicePassword)) {
                ApiResult<ErrorData> errorDataApiResult = new ApiResult<>("Kirish mumkin emas oka", 401);
                httpServletResponse.getWriter().write(gson.toJson(errorDataApiResult));
                httpServletResponse.setStatus(401);
                httpServletResponse.setContentType("application/json");
                httpServletResponse.sendError(401, "parol qani");
            }
        }
        try {
            //TOKEN YOKI BASIC NI OLIB SISTEMAGA KIRITADI
            setUserPrincipalIfAllOk(httpServletRequest);
        } catch (Exception ignored) {
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void setUserPrincipalIfAllOk(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null) {
            User user = null;
            if (authorization.startsWith("Bearer ")) {
                user = getUserFromBearerToken(authorization);
            } else if (authorization.startsWith("Basic ")) {
                user = getUserFromBasicToken(authorization);
            }
            if (user != null) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
    }

    public User getUserFromBasicToken(String token) {
        token = token.substring("Basic".length()).trim();
        byte[] decode = Base64.getDecoder().decode(token);
        token = new String(decode, Charset.defaultCharset());
        String[] split = token.split(":", 2);
        Optional<User> optionalUser = userRepository.findByPhoneNumberAndEnabledIsTrueAndAccountNonExpiredIsTrueAndCredentialsNonExpiredIsTrueAndAccountNonLockedIsTrue(split[0]);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(split[1], user.getPassword()))
                return optionalUser.get();
        }
        return null;
    }

    public User getUserFromBearerToken(String token) {
        token = token.substring("Bearer".length()).trim();
        if (jwtTokenProvider.validToken(token, true)) {
            String userId = jwtTokenProvider.getUserIdFromToken(token, true);
            Optional<User> optionalUser = userRepository.findByIdAndEnabledIsTrueAndAccountNonExpiredIsTrueAndCredentialsNonExpiredIsTrueAndAccountNonLockedIsTrue(UUID.fromString(userId));
            if (optionalUser.isPresent()) {
                return optionalUser.get();
            }
        }
        return null;
    }

}
