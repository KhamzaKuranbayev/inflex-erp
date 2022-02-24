package ai.ecma.appauth.repository;

import ai.ecma.appauth.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Transactional
    @Modifying
    void deleteAllByRoleId(Long roleId);
}
