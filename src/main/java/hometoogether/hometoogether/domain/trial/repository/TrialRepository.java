package hometoogether.hometoogether.domain.trial.repository;

import hometoogether.hometoogether.domain.trial.domain.Trial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrialRepository extends JpaRepository<Trial, Long> {
}
