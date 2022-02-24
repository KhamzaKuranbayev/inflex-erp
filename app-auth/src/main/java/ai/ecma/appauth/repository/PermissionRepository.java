package ai.ecma.appauth.repository;

import ai.ecma.appauth.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    List<Permission> findAllByPageId(Long id);

    @Query(value = "select *\n" +
            "from permission\n" +
            "where id in (\n" +
            "    select permission_id\n" +
            "    from role_page_permission\n" +
            "    where role_page_id in (\n" +
            "        select id\n" +
            "        from role_page\n" +
            "        where role_id = :roleId\n" +
            "    )\n" +
            ")", nativeQuery = true)
    List<Permission> getPermissionByRoleId(Long roleId);


    @Query(nativeQuery = true, value = "select *\n" +
            "from permission\n" +
            "where id in (select permission_id\n" +
            "             from role_page_permission\n" +
            "             where role_page_id in\n" +
            "                   (select id\n" +
            "                    from role_page\n" +
            "                    where role_id in\n" +
            "                          (select role_id\n" +
            "                           from users\n" +
            "                           where id = :userId))\n" +
            ")\n" +
            "  and name in :permissions\n" +
            "limit 1")
    Optional<Permission> findFirstByUserIdAndPermissions(UUID userId, List<String> permissions);


    @Query(nativeQuery = true, value = "select *\n" +
            "from permission\n" +
            "where id in (select permission_id\n" +
            "             from role_page_permission\n" +
            "             where role_page_id in\n" +
            "                   (select id\n" +
            "                    from role_page\n" +
            "                    where role_id in\n" +
            "                          (select role_id\n" +
            "                           from users\n" +
            "                           where id = :userId))\n" +
            ")\n")
    List<Permission> findAllPermissionsByUserId(UUID userId);

    @Query(nativeQuery = true, value = "select name\n" +
            "from permission\n" +
            "where id in\n" +
            "      (select permission_id\n" +
            "       from role_page_permission\n" +
            "       where page_id = :pageId\n" +
            "         and role_page_id\n" +
            "           in\n" +
            "             (select id from role_page where role_id in (select role_id from user_role where user_id = :userId)))")
    List<String> getPermissionsByUserIdAndPageId(UUID userId, Long pageId);
}
