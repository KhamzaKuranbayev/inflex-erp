package ai.ecma.academic.entity;

import ai.ecma.academic.entity.enums.SiteTypeEnum;
import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteStatus extends AbstractEntity {
    //Qaysi statusda ekanligi(REAL, TEST)
    @Enumerated(EnumType.STRING)
    private SiteTypeEnum siteTypeEnum;
}