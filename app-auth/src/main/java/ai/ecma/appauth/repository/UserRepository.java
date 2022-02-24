package ai.ecma.appauth.repository;

import ai.ecma.appauth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByIdAndEnabledIsTrueAndAccountNonExpiredIsTrueAndCredentialsNonExpiredIsTrueAndAccountNonLockedIsTrue(UUID id);

    Optional<User> findByPhoneNumberAndEnabledIsTrueAndAccountNonExpiredIsTrueAndCredentialsNonExpiredIsTrueAndAccountNonLockedIsTrue(String phoneNumber);

    Optional<User> findByPhoneNumberAndEnabledTrueOrEmailAndEnabledTrue(String phoneNumber, String email);

    Optional<User> findByEmailAndEnabledIsTrue(String email);

    boolean existsByPhoneNumberAndEnabledIsTrue(String phoneNumber);

    boolean existsByPhoneNumberAndEnabledIsFalse(String phoneNumber);

    boolean existsByIdNotAndPhoneNumberAndEnabledIsTrue(UUID id, String phoneNumber);

    @Query(value = "select cast(id as varchar) from users where id in(select user_id from user_role where role_id=:deletingRoleId and role_id<>:insteadOfRoleId)", nativeQuery = true)
    List<UUID> findAllUsersIdByRoleId(Long deletingRoleId, Long insteadOfRoleId);

    @Query(value = "select (select count(*)\n" +
            "from (select * from user_role where role_id = :roleId limit 1) as user_role)>0", nativeQuery = true)
    boolean existsUserRoleByRoleId(Long roleId);

    @Query(value = "select count(*)\n" +
            "from users where id in(select user_id from user_role where role_id = :roleId)", nativeQuery = true)
    long countUsersByRoleId(Long roleId);
}

