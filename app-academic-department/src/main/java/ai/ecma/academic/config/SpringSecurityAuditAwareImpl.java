package ai.ecma.academic.config;

import ai.ecma.academic.payload.UserDTO;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;
import java.util.UUID;

import static ai.ecma.academic.utils.CommonUtils.getUserDTOFromRequest;

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
