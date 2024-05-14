package lms.be.repositories;

import lms.be.models.Ethnicity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EthnicityRepository extends JpaRepository<Ethnicity, Integer> {
}
