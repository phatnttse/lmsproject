package lms.be.repositories;

import lms.be.models.Class;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<Class, String> {
}
