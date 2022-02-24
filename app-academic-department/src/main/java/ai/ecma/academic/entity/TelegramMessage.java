package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * TELEGRAM BOT DAGI XABARLAR
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelegramMessage extends AbstractEntity {

    private Integer messageId; //

    private Long chatId; //

}
