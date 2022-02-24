package ai.ecma.appauth.repository;

import ai.ecma.appauth.entity.UserQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


public interface UserQuestionRepository extends JpaRepository<UserQuestion, UUID> {

    int countAllByUserId(UUID userId);

    List<UserQuestion> findAllByUserIdOrderByOrder(UUID userId);

    List<UserQuestion> findAllByUser_PhoneNumberAndUser_EnabledIsTrueOrderByOrder(String user_phoneNumber);

    @Transactional
    @Modifying
    void deleteAllByUserId(UUID userId);


}
