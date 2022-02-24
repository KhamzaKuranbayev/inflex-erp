package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;
import java.util.UUID;

/**
 * passive mentor larga
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MentorPassivity extends AbstractEntity {

    private Integer count;//ogohlantirishlar soni

    private UUID courseId;//QAYSI KURS BO'YICHA OGOHLANTIRILYAPTI(PASSIV O'TAYOTGAN DARSLARI)

    private UUID moduleId;//QAYSI MODULE DA PASSIVLIK(MENTOR KUZATILYAPTI)

    private Date date;//QAYSI SANDA PASSIVLIK KUZATILGAN

}
