package lms.be.repositories;

import lms.be.dtos.UserDTO;
import lms.be.enums.Role;
import lms.be.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    List<User> findByRole(String role);

    int countByRole(String role);
}
