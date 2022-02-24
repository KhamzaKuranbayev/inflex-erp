package ai.ecma.appauth.repository;

import ai.ecma.appauth.entity.SmsCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface SmsCodeRepository extends JpaRepository<SmsCode, UUID> {


    @Query(value = "SELECT :limitSms <= (SELECT COUNT(*)\n" +
            "                     FROM sms_code\n" +
            "                     WHERE phone_number = :phoneNumber\n" +
            "                       AND created_at > (SELECT current_timestamp - (INTERVAL '1' hour) * :limitHour) AND ignored=false)", nativeQuery = true)
    boolean overLimitSmsCodeByPhoneNumberAndDuration(Integer limitSms, String phoneNumber, Integer limitHour);

    Optional<SmsCode> findFirstByPhoneNumberOrderByCreatedAtDesc(String phoneNumber);

    @Transactional
    @Modifying
    @Query(value = "delete from sms_code where id=:id",nativeQuery = true)
    void deleteBySmsCodeId(UUID id);

}


