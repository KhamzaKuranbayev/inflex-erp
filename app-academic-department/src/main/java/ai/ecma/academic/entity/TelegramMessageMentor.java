package ai.ecma.academic.entity;

import ai.ecma.academic.entity.enums.MessageTypeEnum;
import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.UUID;

/**
 * ASSISTANT YOKI MENTOR LAR YOZGAN XABARLARI (task-chat page dagi)
 * ASSISTANT YOKI MENTOR OQUVCHIGA YOZGAN XABARLARI
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TelegramMessageMentor extends AbstractEntity {

    private Integer messageId;//TELEGRAMDAGI XABARNING ID SI

    private Long userChatId;//BOT GA YUBORILGAN XABARNING CHAT ID SI

    private UUID taskOrSupportId;//MESSAGE DA TASK JONATILDIMI YOKI SUPPORT KORSATILDIMI

    @Enumerated(EnumType.STRING)
    private MessageTypeEnum messageType;//TASKMI || SUPPORTMI

}
