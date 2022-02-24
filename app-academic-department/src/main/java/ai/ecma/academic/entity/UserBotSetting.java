package ai.ecma.academic.entity;

import ai.ecma.academic.entity.enums.BotOption;
import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

/**
 * HAR BIR FOYDALNUVCHIGA BOT UCHUN BERILADIGAN RUXSATLAR
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"botOption", "userId"}))
public class UserBotSetting extends AbstractEntity {

    /**
    FOYDALNUVCHI
     */
    @Column(nullable = false)
    private UUID userId;

    /**
    FOYDALNUVCHINING BOT UCHUN RUXSATLARI
     */
    @Enumerated(EnumType.STRING)
    private BotOption botOption;
}
