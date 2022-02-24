package ai.ecma.appauth.repository;

import ai.ecma.appauth.entity.RolePagePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolePagePermissionRepository extends JpaRepository<RolePagePermission,Long> {
    Optional<RolePagePermission> findByRolePageIdAndPermissionId(Long rolePage_id, Long permissionId);

}
