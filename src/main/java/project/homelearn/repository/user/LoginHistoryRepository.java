package project.homelearn.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import project.homelearn.entity.user.LoginHistory;

import java.time.LocalDateTime;
import java.util.List;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
    List<LoginHistory> findByLoginDateTimeBetween(LocalDateTime start, LocalDateTime end);
}