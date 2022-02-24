package ai.ecma.appauth.repository;

import ai.ecma.appauth.entity.RolePage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolePageRepository extends JpaRepository<RolePage,Long> {
    Optional<RolePage> findByRoleIdAndPageId(Long role_id, Long pageId);
}
