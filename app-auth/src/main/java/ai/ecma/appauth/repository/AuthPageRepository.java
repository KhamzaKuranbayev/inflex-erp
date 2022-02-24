package ai.ecma.appauth.repository;

import ai.ecma.appauth.entity.AuthPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AuthPageRepository extends JpaRepository<AuthPage, Long> {
    List<AuthPage> findAllByDepartmentId(Long department_id);

    @Query(value = "select *\n" +
            "from auth_page\n" +
            "where id in (select page_id from role_page where role_id in (select role_id from user_role where user_id = :userId))", nativeQuery = true)
    List<AuthPage> findAllByUserId(UUID userId);
}
