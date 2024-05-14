package lms.be.repositories;

import lms.be.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, String> {

    boolean existsById(String id);
    Optional<Department> findByDepartmentId(String id);
}
