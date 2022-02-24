package ai.ecma.appaccount.config;

import ai.ecma.appaccount.payload.UserDTO;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;
import java.util.UUID;

import static ai.ecma.appaccount.utils.CommonUtils.getUserDTOFromRequest;

public class SpringSecurityAuditAwareImpl implements AuditorAware<UUID> {

    @Override
    public Optional<UUID> getCurrentAuditor() {
        UserDTO userDTOFromRequest = getUserDTOFromRequest();
        if (userDTOFromRequest != null) {
            return Optional.of(userDTOFromRequest.getId());
        }
        return Optional.empty();
    }
}
