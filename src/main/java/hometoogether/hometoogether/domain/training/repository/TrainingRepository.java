package hometoogether.hometoogether.domain.training.repository;



import hometoogether.hometoogether.domain.training.domain.Training;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRepository extends JpaRepository<Training, Long> {
}
