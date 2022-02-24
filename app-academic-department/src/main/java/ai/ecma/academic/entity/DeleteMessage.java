package ai.ecma.academic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

/**
 * TELEGRAM botdagi XABARLARNI O`CHIRISH UCHUN KERAK BO`LADIGAN CLASS
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DeleteMessage {

    @Id
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    private Long chatId; // O`CHIRILADIGAN XABARNING CHAT ID  SI

    private Integer messageId; //O`CHIRILADIGAN XABAR ID SI

}
