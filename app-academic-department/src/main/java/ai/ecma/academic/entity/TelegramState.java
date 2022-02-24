package ai.ecma.academic.entity;

import ai.ecma.academic.entity.enums.BotState;
import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

/**
 * HAR BIR BOT NING FOYDALANUVCHISI UCHUN TELEGRAM_BOT NING HOLATI
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelegramState extends AbstractEntity {


    @Enumerated(EnumType.STRING)
    private BotState botState;//BOT AYNI VAQTDA FOYDALNUVCHI UCHUN QAY HOLATDA TURGANI

    @Column(nullable = false, unique = true)
    private Long chatId;//USERNING CHAT ID SI

//    @ManyToOne(fetch = FetchType.LAZY)
    private UUID userId;//FOYDALNUVCHI

    private Integer passwordCheckCount;//PASSWORDNI NECHA MARTA TEKSHIRGANLIGI

    private UUID replyMessageId;//REPLY QILINGAN XABARNING ID SI

    private String replyMessageType;//REPLY QILINGAN XABAR QAYSI TURGA MANSUB EKANLIGI

    private String phone;//FOYDALANUVCHINING TELEFON RAQAMI

}
