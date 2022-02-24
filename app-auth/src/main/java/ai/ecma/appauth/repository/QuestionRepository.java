package ai.ecma.appauth.repository;

import ai.ecma.appauth.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuestionRepository extends JpaRepository<Question, Long> {
    boolean existsByQuestion(String question);
}
