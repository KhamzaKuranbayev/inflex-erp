package ai.ecma.appauth.repository;

import ai.ecma.appauth.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findAllByModuleId(Long module_id);

}
