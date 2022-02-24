package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * CODINGBATDA BO'LGANI KABI TASKNI BAJARISH UCHUN YORDAMLASHUVCHI
 * HINTLAR KO'RSATILADI, TASKEXERCISEGA MANYTOONE QILIB BIRIKTIRIB KETILGAN
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Hint extends AbstractEntity {
    @Column(columnDefinition = "text")
    private String hint;//HINT (PODSKAZKA) MISOL: indexOf() METODINI ISHLATING

    private Integer ord;//TARTIB RAQAM : BITTA TASKDA HINTLAR
                        // BIR NECHTA BOLISHI MUMKIN BU UNING TARTIB RAQAMI
}
