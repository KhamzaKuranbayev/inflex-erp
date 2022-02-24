package ai.ecma.academic.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * ASSISTENT YOKI MENTOR STUDENTNING SAVOLIGA FAQAT REPLY QILIB JAVOB BERSA
 * SHU ENTITYGA SAQLANADI.(CHAT, SAVOL JAVOB BO'LIMI)
 */


public interface KetmonRepository extends JpaRepository<Ketmon, UUID> {
    Optional<Ketmon> findByName(String name);
}
