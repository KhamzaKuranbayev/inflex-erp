package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * CODINGBATDAGIGA O'XSHAGAN TASKLARNI AVTOMATIK TEKSHIRISH UCHUN ISHLATILADI
 * TASK EXERCISES UCHUN KIRITILADIGAN PARAMLAR
 * String name,Integer age .etc
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Param extends AbstractEntity {

    private String dataType;//Parametrni turi String,Integer,Double va boshaqlar

    private String name;//parametrga berilgan nom ixtiyoriy
}
