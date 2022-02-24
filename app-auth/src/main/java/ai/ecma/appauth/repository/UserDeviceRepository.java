package ai.ecma.appauth.repository;

import ai.ecma.appauth.entity.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserDeviceRepository extends JpaRepository<UserDevice, UUID> {

    boolean existsByIdAndUserId(UUID id, UUID user_id);
}
