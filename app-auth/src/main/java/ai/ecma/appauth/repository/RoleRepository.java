package ai.ecma.appauth.repository;

import ai.ecma.appauth.entity.Role;
import ai.ecma.appauth.entity.enums.RoleType;
import ai.ecma.appauth.utils.RestConstants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findFirstByRoleType(RoleType roleType);

    boolean existsByName(String name);

    boolean existsByRoleType(RoleType roleType);

    boolean existsByIdNotAndName(Long id, String name);

    boolean existsByIdNotAndRoleType(Long id, RoleType roleType);

    @Transactional
    @Modifying
    @Query(value = RestConstants.INITIAL_EXECUTING_QUERY, nativeQuery = true)
    void executingQueryEveryRunning();

    List<Role> findAllByRoleTypeIsNotIn(Collection<RoleType> roleType);
}
