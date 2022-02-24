package ai.ecma.academic.feign;

import ai.ecma.academic.config.FeignConfiguration;
import ai.ecma.academic.payload.UserDTO;
import ai.ecma.academic.utils.RestConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Set;

//@Component(value = "authFeign")
@FeignClient(name = "authFeign", url = RestConstants.AUTH_SERVICE, configuration = FeignConfiguration.class)
public interface AuthFeign {
    @PostMapping(value = "/checkPermission", produces = "application/json;charset=utf-8")
    UserDTO checkPermission(@RequestHeader("Authorization") String token,
                            @RequestBody Set<String> permissions);

    @GetMapping(value = "/checkToken", produces = "application/json;charset=utf-8")
    UserDTO checkUser(@RequestHeader("Authorization") String token);
}
